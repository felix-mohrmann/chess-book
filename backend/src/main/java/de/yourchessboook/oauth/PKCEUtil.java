package de.yourchessboook.oauth;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

@Getter
@Service
public class PKCEUtil {
    private final String code_verifier = generateRandomCodeVerifier();

    private final String code_challenge_method = "S256";
    private final String code_challenge = generateCodeChallenge(code_verifier);
    private final String state = generateRandomState();


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
}