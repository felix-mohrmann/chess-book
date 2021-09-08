package de.yourchessboook.service;

import de.yourchessboook.rest.lichess.LichessClient;
import de.yourchessboook.rest.lichess.LichessGameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LichessService {
    private final LichessClient lichessClient;

    @Autowired
    public LichessService(LichessClient lichessClient) {
        this.lichessClient = lichessClient;
    }

}
