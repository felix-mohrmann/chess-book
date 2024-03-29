package com.yourchessboook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yourchessboook.model.GameEntity;
import com.yourchessboook.model.OpeningModel;
import com.yourchessboook.repo.GameRepository;
import com.yourchessboook.repo.OpeningRepository;
import com.yourchessboook.rest.lichess.LichessClient;
import com.yourchessboook.rest.lichess.LichessGameDto;
import com.yourchessboook.rest.lichess.LichessGamesDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LichessService {
    private static final String DRAW = "draw";
    private final LichessClient lichessClient;
    private final GameRepository gameRepository;
    private final OpeningRepository openingRepository;

    @Autowired
    public LichessService(LichessClient lichessClient, GameRepository gameRepository,
            OpeningRepository openingRepository) {
        this.lichessClient = lichessClient;
        this.gameRepository = gameRepository;
        this.openingRepository = openingRepository;
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
            gameEntity.setWinner(
                    evaluate(lichessGameDto.getWinner(), lichessGameDto.getPlayers().getWhite().getUser().getName(),
                            lichessGameDto.getPlayers().getBlack().getUser().getName()));
            gameRepository.save(gameEntity);
        }
    }

    private String evaluate(String winner, String white, String black) {
        if (Objects.equals(winner, "white")) {
            return white;
        } else if (Objects.equals(winner, "black")) {
            return black;
        } else {
            return DRAW;
        }
    }

    public List<OpeningModel> getMostFrequentWhiteOpenings(String username) {
        List<GameEntity> gameEntityList = gameRepository.findAllByWhitePlayerIsOrderByOpening(username);

        return sortAndFilterGames(username, gameEntityList);
    }

    public List<OpeningModel> getMostFrequentBlackOpenings(String username) {
        List<GameEntity> gameEntityList = gameRepository.findAllByBlackPlayerIsOrderByOpening(username);

        return sortAndFilterGames(username, gameEntityList);
    }

    private List<OpeningModel> sortAndFilterGames(String username, List<GameEntity> gameEntityList) {
        List<OpeningModel> unsortedOpeningList = new ArrayList<>();

        for (GameEntity gameEntity : gameEntityList) {
            String fullOpeningName = gameEntity.getOpening();
            String[] splitName = fullOpeningName.split(": ");
            String openingName = splitName[0];

            // if not existing: build new, according to win/loose/draw
            if (!present(username, unsortedOpeningList, gameEntity, openingName)) {
                if (gameEntity.getWinner().equals(username)) {
                    unsortedOpeningList.add(OpeningModel.builder()
                            .name(openingName)
                            .fen(openingRepository.findByName(openingName).isPresent()
                                    ? openingRepository.findByName(openingName).get().getFen()
                                    : "not Found")
                            .numWins(1)
                            .build());
                } else if (gameEntity.getWinner().equals(DRAW)) {
                    unsortedOpeningList.add(OpeningModel.builder()
                            .name(openingName)
                            .fen(openingRepository.findByName(openingName).isPresent()
                                    ? openingRepository.findByName(openingName).get().getFen()
                                    : "not Found")
                            .numDraw(1)
                            .build());
                } else {
                    unsortedOpeningList.add(OpeningModel.builder()
                            .name(openingName)
                            .fen(openingRepository.findByName(openingName).isPresent()
                                    ? openingRepository.findByName(openingName).get().getFen()
                                    : "not Found")
                            .numLosses(1)
                            .build());
                }
            }
        }

        // sort opening list depending on most played
        List<OpeningModel> sortedOpeningList = unsortedOpeningList.stream()
                .sorted(Comparator.comparing(OpeningModel::getTotalGames).reversed())
                .collect(Collectors.toList());

        // Cut down to top 3
        while (sortedOpeningList.size() > 3) {
            sortedOpeningList.remove(3);
        }

        return sortedOpeningList;
    }

    private boolean present(String username, List<OpeningModel> unsortedOpeningList, GameEntity gameEntity,
            String openingName) {
        for (OpeningModel opening : unsortedOpeningList) { // opening existing?
            if (opening.getName().equals(openingName)) {
                if (gameEntity.getWinner().equals(username)) {
                    opening.win();
                    return true;
                } else if (gameEntity.getWinner().equals(DRAW)) {
                    opening.draw();
                    return true;
                } else {
                    opening.loose();
                    return true;
                }
            }
        }
        return false;
    }
}
