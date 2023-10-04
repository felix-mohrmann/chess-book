package de.yourchessboook.controller;

import de.yourchessboook.api.LichessGames;
import de.yourchessboook.api.Openings;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static de.yourchessboook.controller.LichessControllerImpl.LICHESS_CONTROLLER;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Tag(name = LICHESS_CONTROLLER, description = "Query Lichess API")
@Api(tags = LICHESS_CONTROLLER)
public interface LichessController {

        @Operation(summary = "Fetching all user games from Lichess.")
        @GetMapping(value = "/games/{username}", produces = APPLICATION_JSON_VALUE)
        ResponseEntity<LichessGames> fetchGames(@PathVariable("username") String username);

        @Operation(summary = "Loading Top3 most played openings with White from database.")
        @GetMapping("/openings/white/{username}")
        ResponseEntity<Openings> getMostFrequentWhiteOpenings(@PathVariable("username") String username);

        @Operation(summary = "Loading Top3 most played openings with Black from database.")
        @GetMapping("/openings/black/{username}")
        ResponseEntity<Openings> getMostFrequentBlackOpenings(@PathVariable("username") String username);
}
