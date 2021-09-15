package de.yourchessboook.service;

import de.yourchessboook.api.Opening;
import de.yourchessboook.model.GameEntity;
import de.yourchessboook.repo.GameRepository;
import de.yourchessboook.rest.lichess.LichessClient;
import de.yourchessboook.rest.lichess.LichessGameDto;
import de.yourchessboook.rest.lichess.LichessGamesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class LichessService {
    private final LichessClient lichessClient;
    private final GameRepository gameRepository;

    @Autowired
    public LichessService(LichessClient lichessClient, GameRepository gameRepository) {
        this.lichessClient = lichessClient;
        this.gameRepository = gameRepository;
    }

    public LichessGamesDto getGames(String username) {
        LichessGamesDto lichessGamesDto = lichessClient.getGames(username);

        List<LichessGameDto> lichessGameDtoList = lichessGamesDto.getLichessGameDtos();
        if (lichessGameDtoList.isEmpty()) {
            return lichessGamesDto;
        }

        saveToDB(lichessGameDtoList);

        return lichessGamesDto;
    }

    private void saveToDB(List<LichessGameDto> lichessGameDtoList) {
        for (LichessGameDto lichessGameDto : lichessGameDtoList) {
            GameEntity gameEntity = new GameEntity();
            gameEntity.setOpening(lichessGameDto.getOpening().getName());
            gameEntity.setMoves(lichessGameDto.getMoves());
            gameEntity.setWhitePlayer(lichessGameDto.getPlayers().getWhite().getUser().getName());
            gameEntity.setBlackPlayer(lichessGameDto.getPlayers().getBlack().getUser().getName());
            gameEntity.setWinner(evaluate(lichessGameDto.getWinner(), lichessGameDto.getPlayers().getWhite().getUser().getName(), lichessGameDto.getPlayers().getBlack().getUser().getName()));
            gameRepository.save(gameEntity);
        }
    }

    private String evaluate(String winner, String white, String black) {
        if (Objects.equals(winner, "white")) {
            return white;
        } else if (Objects.equals(winner, "black")) {
            return black;
        } else {
            return "draw";
        }
    }

    public ResponseEntity<List<Opening>> getMostFrequentWhiteOpenings(String username) {
        List<GameEntity> gameEntityList = gameRepository.findAllByWhitePlayerIsOrderByOpening(username);

        return sortAndFilterGames(username, gameEntityList);
    }

    public ResponseEntity<List<Opening>> getMostFrequentBlackOpenings(String username) {
        List<GameEntity> gameEntityList = gameRepository.findAllByBlackPlayerIsOrderByOpening(username);

        return sortAndFilterGames(username, gameEntityList);
    }

    private ResponseEntity<List<Opening>> sortAndFilterGames(String username, List<GameEntity> gameEntityList) {
        List<Opening> unsortedOpeningList = new ArrayList<>();

        for (GameEntity gameEntity : gameEntityList) {
            String fullOpeningName = gameEntity.getOpening();
            String[] splitOpeningName = fullOpeningName.split(": ");
            String openingName = splitOpeningName[0]; //cut down to prefix

            boolean found = false;
            for (Opening opening : unsortedOpeningList) { //opening existing?
                if (opening.getName().equals(openingName)) {
                    found = true;
                    if (gameEntity.getWinner().equals(username)) {
                        opening.win();
                    } else if (gameEntity.getWinner().equals("draw")) {
                        opening.draw();
                    } else {
                        opening.loose();
                    }
                }
            }

            if (!found) { // if not existing: build new, according to win/loose/draw
                if (gameEntity.getWinner().equals(username)) {
                    unsortedOpeningList.add(Opening.builder()
                            .name(openingName)
                            .numWins(1)
                            .build());
                } else if (gameEntity.getWinner().equals("draw")) {
                    unsortedOpeningList.add(Opening.builder()
                            .name(openingName)
                            .numDraw(1)
                            .build());
                } else {
                    unsortedOpeningList.add(Opening.builder()
                            .name(openingName)
                            .numLosses(1)
                            .build());
                }
            }
        }

        //sort opening list depending on most played
        List<Opening> sortedOpeningList = unsortedOpeningList.stream()
                .sorted(Comparator.comparing(Opening::getTotalGames).reversed())
                .collect(Collectors.toList());

        //Cut down to top 3
        while(sortedOpeningList.size() != 3){
            sortedOpeningList.remove(3);
        }

        return ok(sortedOpeningList);
    }
}
