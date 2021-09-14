package de.yourchessboook.oauth;

import de.yourchessboook.service.PKCEUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class LichessLoginAPI {
    private final static String LICHESS_LOGIN_URL = "https://lichess.org/api/token";
    private final RestTemplate restTemplate;
    private final PKCEUtil pkceUtil;

    @Autowired
    public LichessLoginAPI(RestTemplate restTemplate, PKCEUtil pkceUtil) {
        this.restTemplate = restTemplate;
        this.pkceUtil = pkceUtil;
    }

    public LichessAccessTokenResponseDto getToken(String code, String verifier) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("code_verifier", verifier);
        map.add("redirect_uri", "http://localhost:3000/oauth/lichess_redirect");
        map.add("client_id", "chess-book");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<LichessAccessTokenResponseDto> response = restTemplate
                .exchange(LICHESS_LOGIN_URL, HttpMethod.POST, entity, LichessAccessTokenResponseDto.class);
        return response.getBody();
    }
}
