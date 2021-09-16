package de.yourchessboook.controller;

import de.yourchessboook.api.LichessGames;
import de.yourchessboook.model.OpeningModel;
import de.yourchessboook.rest.lichess.LichessGamesDto;
import de.yourchessboook.service.LichessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<LichessGames> getGames(String username) {
        LichessGamesDto lichessGamesDto = lichessService.getGames(username);
        LichessGames lichessGames = map(lichessGamesDto);
        return ok(lichessGames);
    }

    @Override
    public ResponseEntity<List<OpeningModel>> getMostFrequentWhiteOpenings(String username) {
        List<OpeningModel> openingList = lichessService.getMostFrequentWhiteOpenings(username)

        return ok();
    }

    @Override
    public ResponseEntity<List<OpeningModel>> getMostFrequentBlackOpenings(String username) {
        return ok(lichessService.getMostFrequentBlackOpenings(username));
    }
}
