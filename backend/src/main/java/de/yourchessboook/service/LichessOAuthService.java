package de.yourchessboook.service;

import de.yourchessboook.config.LichessClientConfigProperties;
import de.yourchessboook.oauth.LichessAccessTokenResponseDto;
import de.yourchessboook.oauth.LichessLoginAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LichessOAuthService {

    private final LichessLoginAPI lichessLoginAPI;
    private final LichessClientConfigProperties lichessClientConfigProperties;

    @Autowired
    public LichessOAuthService(LichessLoginAPI lichessLoginAPI, LichessClientConfigProperties lichessClientConfigProperties) {
        this.lichessLoginAPI = lichessLoginAPI;
        this.lichessClientConfigProperties = lichessClientConfigProperties;
    }

    public String authenticate(String code, String verifier) {
            LichessAccessTokenResponseDto responseDto = lichessLoginAPI.getToken(code, verifier);
            String token = responseDto.getAccess_token();
            lichessClientConfigProperties.setAccessToken(token);
            return token;
    }
}
