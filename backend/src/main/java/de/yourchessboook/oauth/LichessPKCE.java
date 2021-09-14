package de.yourchessboook.oauth;

import java.awt.Desktop;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.Random;
import java.util.stream.Collectors;

import com.sun.net.httpserver.HttpServer;

public class LichessPKCE {

    public static String lichessUri = "https://lichess.org";

    /**
     * This demo application will launch a Web Browser,
     * where authentication with Lichess can be made,
     * for authorization of this demo application to
     * request the e-mail address of the authenticating
     * Lichess user - and if granted - the e-mail address
     * will be fetched and printed on standard output.
     */
    public static void main(String... args) throws Exception {

        // Perform the OAuth2 PKCE flow
        var access_token = login();

        System.out.println(access_token);

        // Logout
        logout(access_token);

    }

    public static String login() throws Exception {

        // Prepare a new login.
        // We will generate a lot of parameters which will be used in this login,
        // and then the parameters are thrown away, not to be re-used.
        // I.e, next login request will have new parameters generated.

        // Set up a local bind address which we will use in redirect_uri
        var local = new InetSocketAddress(InetAddress.getLoopbackAddress(), 0);
        var httpServer = HttpServer.create(local, 0);
        var redirectHost = local.getAddress().getHostAddress();
        var redirectPort = httpServer.getAddress().getPort();

        var code_verifier = generateRandomCodeVerifier();

        var code_challenge_method = "S256";
        var code_challenge = generateCodeChallenge(code_verifier);
        var response_type = "code";
        var client_id = "chess-book";
        var redirect_uri = "http://" + redirectHost + ":" + redirectPort;
        var state = generateRandomState();

        var parameters = Map.of(
                "code_challenge_method", code_challenge_method,
                "code_challenge", code_challenge,
                "response_type", response_type,
                "client_id", client_id,
                "redirect_uri", redirect_uri,
                "state", state
        );

        var paramString = parameters.entrySet().stream()
                .map(kv -> kv.getKey() + "=" + kv.getValue())
                .collect(Collectors.joining("&"));

        // Front Channel URL, all these parameters are non-sensitive.
        // The actual authentication between User and Lichess happens outside this demo application,
        // i.e. in the browser over HTTPS.
        var frontChannelUrl = URI.create(lichessUri + "/oauth" + "?" + paramString);

        // Prepare for handling the upcoming redirect,
        // after User has authenticated with Lichess.
        // The random code_verifier we generated for this single login,
        // will be sent to Lichess on a "Back Channel" so they can verify that
        // the Front Channel request really came from us.
        var cf = registerRedirectHandler(httpServer, parameters, code_verifier);

        // Now we let the User authorize with Lichess,
        // using their browser
        if (Desktop.isDesktopSupported()) {
            var desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(frontChannelUrl);
            } else {
                System.out.format("%s%n%n%s%n  %s%n%n",
                        "Doh, Desktop.Action.BROWSE not supported...",
                        "Could you manually go to the following URL :) ?",
                        frontChannelUrl);
            }
        } else {
            System.out.format("%s%n%n%s%n  %s%n%n",
                    "Doh, Desktop not supported...",
                    "Could you manually go to the following URL :) ?",
                    frontChannelUrl);
        }

        // Blocking until user has authorized,
        // and we've exchanged the incoming authorization code for an access-token
        var access_token = cf.get();

        httpServer.stop(0);

        return access_token;
    }

    static void logout(String access_token) throws Exception {
        var logoutRequest = HttpRequest.newBuilder(URI.create(lichessUri + "/api/token"))
                .DELETE()
                .header("authorization", "Bearer " + access_token)
                .build();

        var response = HttpClient.newHttpClient().send(logoutRequest, BodyHandlers.discarding());
        var statusCode = response.statusCode();
        if (statusCode != 204) {
            System.out.println("/api/token - " + response.statusCode());
        }
    }

    static String generateRandomCodeVerifier() {
        var bytes = new byte[32];
        new Random().nextBytes(bytes);
        return encodeToString(bytes);
    }

    static String generateCodeChallenge(String code_verifier) {
        var asciiBytes = code_verifier.getBytes(StandardCharsets.US_ASCII);
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException nsa_ehhh) {
            throw new RuntimeException(nsa_ehhh);
        }

        var s256bytes = md.digest(asciiBytes);

        return encodeToString(s256bytes);
    }

    static String generateRandomState() {
        var bytes = new byte[16];
        new Random().nextBytes(bytes);
        // Not sure how long the parameter "should" be,
        // going for 8 characters here...
        return encodeToString(bytes).substring(0, 8);
    }

    static String encodeToString(byte[] bytes) {
        return Base64.getUrlEncoder().encodeToString(bytes)
                .replaceAll("=", "")
                .replaceAll("\\+", "-")
                .replaceAll("/", "_");
    }

    static CompletableFuture<String> registerRedirectHandler(HttpServer httpServer, Map<String, String> requestParams, String code_verifier) {
        var cf = new CompletableFuture<String>();
        httpServer.createContext("/",
                (exchange) -> {
                    httpServer.removeContext("/");

                    // The redirect arrives...
                    var query = exchange
                            .getRequestURI()
                            .getQuery();

                    var inparams = Arrays.stream(query.split("&"))
                            .collect(Collectors.toMap(
                                    s -> s.split("=")[0],
                                    s -> s.split("=")[1]
                            ));

                    var code = inparams.get("code");
                    var state = inparams.get("state");

                    if (!state.equals(requestParams.get("state"))) {
                        cf.completeExceptionally(new Exception("The \"state\" parameter we sent and the one we received didn't match!"));
                        return;
                    }

                    if (code == null) {
                        exchange.sendResponseHeaders(503, -1);
                        cf.completeExceptionally(new Exception("Authorization Failed"));
                        return;
                    }

                    // We have received metadata from Lichess,
                    // about the fact that the User has authorized us - yay!

                    // Let's respond with a nice HTML page in celebration.
                    var responseBytes = "<html><body><h1>Success, you may close this page</h1></body></html>".getBytes();
                    exchange.sendResponseHeaders(200, responseBytes.length);
                    exchange.getResponseBody().write(responseBytes);


                    // Now,
                    // let's go to Lichess and ask for a token - using the metadata we've received
                    var tokenParameters = Map.of(
                            "code_verifier", code_verifier,
                            "grant_type", "authorization_code",
                            "code", code,
                            "redirect_uri", requestParams.get("redirect_uri"),
                            "client_id", requestParams.get("client_id")
                    );

                    var tokenParamsString = tokenParameters.entrySet().stream()
                            .map(kv -> kv.getKey() + "=" + kv.getValue())
                            .collect(Collectors.joining("&"));

                    var tokenRequest = HttpRequest.newBuilder(URI.create(lichessUri + "/api/token"))
                            .POST(BodyPublishers.ofString(tokenParamsString))
                            .header("content-type", "application/x-www-form-urlencoded")
                            .build();

                    try {
                        var response = HttpClient.newHttpClient().send(tokenRequest, BodyHandlers.ofString());
                        var statusCode = response.statusCode();
                        var body = response.body();

                        if (statusCode != 200) {
                            System.out.println("/api/token - " + statusCode);
                        }
                        var access_token = parseField("access_token", body);

                        if (access_token == null) {
                            System.out.println("Body: " + body);
                            cf.completeExceptionally(new Exception("Authorization Failed"));
                            return;
                        }

                        // Ok, we have successfully retrieved a token which we can use
                        // to fetch the e-mail address
                        cf.complete(access_token);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        httpServer.start();
        return cf;
    }

    // Light-weight fragile "json" ""parser""...
    static String parseField(String field, String body) {
        try {
            int start = body.indexOf(field) + field.length() + 3;
            int stop = body.indexOf("\"", start);
            return body.substring(start, stop);
        } catch (Exception e) {
            return null;
        }
    }
}