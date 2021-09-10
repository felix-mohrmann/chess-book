package de.yourchessboook.controller;

import de.yourchessboook.api.LichessGames;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static de.yourchessboook.controller.LichessControllerImpl.LICHESS_CONTROLLER;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Tag(name = LICHESS_CONTROLLER, description = "Query Lichess API")
@Api(
        tags = LICHESS_CONTROLLER
)
@RequestMapping("/games/{username}")
public interface LichessController {

    @Operation(summary = "Fetching all user games from Lichess.")
    @GetMapping(
            produces = APPLICATION_JSON_VALUE
    )
    ResponseEntity<LichessGames> getGames(@PathVariable ("username") String username);
}
