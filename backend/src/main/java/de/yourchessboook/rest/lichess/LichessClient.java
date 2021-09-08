package de.yourchessboook.rest.lichess;

import de.yourchessboook.config.LichessClientConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LichessClient {

    private final LichessClientConfigProperties lichessClientConfigProperties;
    private final LichessAPI lichessAPI;

    @Autowired
    public LichessClient(LichessClientConfigProperties lichessClientConfigProperties, LichessAPI lichessAPI) {
        this.lichessClientConfigProperties = lichessClientConfigProperties;
        this.lichessAPI = lichessAPI;
    }

    public LichessGameDto getLastGame(String name){
        return lichessAPI.getUsersLastGame(getAccessToken(), name);
    }

    private String getAccessToken() {
        return lichessClientConfigProperties.getAccessToken();
    }
}
