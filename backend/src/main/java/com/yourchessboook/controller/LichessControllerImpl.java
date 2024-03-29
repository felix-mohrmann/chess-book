package com.yourchessboook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.yourchessboook.api.LichessGames;
import com.yourchessboook.api.Openings;
import com.yourchessboook.model.OpeningModel;
import com.yourchessboook.rest.lichess.LichessGamesDto;
import com.yourchessboook.service.LichessService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin
public class LichessControllerImpl extends LichessControllerMapper implements LichessController {

    public static final String LICHESS_CONTROLLER = "Lichess";
    private final LichessService lichessService;

    @Autowired
    public LichessControllerImpl(LichessService lichessService) {
        this.lichessService = lichessService;
    }

    @Override
    public ResponseEntity<LichessGames> fetchGames(String username) {
        LichessGamesDto lichessGamesDto = lichessService.getGames(username);
        LichessGames lichessGames = map(lichessGamesDto);
        return ok(lichessGames);
    }

    @Override
    public ResponseEntity<Openings> getMostFrequentWhiteOpenings(String username) {
        List<OpeningModel> openingList = lichessService.getMostFrequentWhiteOpenings(username);
        Openings openings = map(openingList);
        return ok(openings);
    }

    @Override
    public ResponseEntity<Openings> getMostFrequentBlackOpenings(String username) {
        List<OpeningModel> openingList = lichessService.getMostFrequentBlackOpenings(username);
        Openings openings = map(openingList);
        return ok(openings);
    }
}
