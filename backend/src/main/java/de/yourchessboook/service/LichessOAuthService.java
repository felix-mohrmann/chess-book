package de.yourchessboook.service;

import de.yourchessboook.oauth.LichessAccessTokenResponseDto;
import de.yourchessboook.oauth.LichessLoginAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LichessOAuthService {

    private final LichessLoginAPI lichessLoginAPI;

    @Autowired
    public LichessOAuthService(LichessLoginAPI lichessLoginAPI) {
        this.lichessLoginAPI = lichessLoginAPI;
    }

    public String authenticate(String code, String verifier) {
            LichessAccessTokenResponseDto responseDto = lichessLoginAPI.getToken(code, verifier);
            return responseDto.getAccess_token();
    }
}
