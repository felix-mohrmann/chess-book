package com.yourchessboook.rest.lichess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yourchessboook.config.LichessClientConfigProperties;

@Component
public class LichessClient {

    private final LichessClientConfigProperties lichessClientConfigProperties;
    private final LichessAPI lichessAPI;

    @Autowired
    public LichessClient(LichessClientConfigProperties lichessClientConfigProperties, LichessAPI lichessAPI) {
        this.lichessClientConfigProperties = lichessClientConfigProperties;
        this.lichessAPI = lichessAPI;
    }

    public LichessGamesDto getGames(String name) {
        return lichessAPI.getGames(getAccessToken(), name);
    }

    private String getAccessToken() {
        return lichessClientConfigProperties.getAccessToken();
    }
}
