package com.yourchessboook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yourchessboook.config.LichessClientConfigProperties;
import com.yourchessboook.oauth.LichessAccessTokenResponseDto;
import com.yourchessboook.oauth.LichessLoginAPI;

@Service
public class LichessOAuthService {

    private final LichessLoginAPI lichessLoginAPI;
    private final LichessClientConfigProperties lichessClientConfigProperties;

    @Autowired
    public LichessOAuthService(LichessLoginAPI lichessLoginAPI,
            LichessClientConfigProperties lichessClientConfigProperties) {
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
