package de.yourchessboook.controller;

import de.yourchessboook.api.LichessGame;
import de.yourchessboook.api.LichessGames;
import de.yourchessboook.api.Opening;
import de.yourchessboook.api.Openings;
import de.yourchessboook.model.OpeningModel;
import de.yourchessboook.rest.lichess.LichessGameDto;
import de.yourchessboook.rest.lichess.LichessGamesDto;

import java.util.List;

public class LichessControllerMapper {

    public LichessGames map(LichessGamesDto lichessGamesDto) {
        LichessGames lichessGames = new LichessGames();
        List<LichessGameDto> lichessGameDtos = lichessGamesDto.getLichessGameDtos();
        for (LichessGameDto lichessGameDto : lichessGameDtos) {
            LichessGame lichessGame = map(lichessGameDto);
            lichessGames.addLichessGame(lichessGame);
        }
        return lichessGames;
    }

    public LichessGame map(LichessGameDto lichessGameDto) {
        return LichessGame.builder()
                .moves(lichessGameDto.getMoves())
                .winner(lichessGameDto.getWinner())
                .opening(lichessGameDto.getOpening().getName())
                .status(lichessGameDto.getStatus())
                .players(new String[]{lichessGameDto.getPlayers().getWhite().getUser().getName(),
                        lichessGameDto.getPlayers().getBlack().getUser().getName()}).build();
    }

    public Openings map(List<OpeningModel> openingList) {
        Openings openings = new Openings();
        for (OpeningModel openingModel : openingList) {
            openings.addOpening(map(openingModel));
        }
        return openings;
    }

    private Opening map(OpeningModel openingModel) {
        return Opening.builder()
                .name(openingModel.getName())
                .numWins(openingModel.getNumWins())
                .numDraw(openingModel.getNumDraw())
                .numLosses(openingModel.getNumLosses())
                .winPercentage(openingModel.getWinningPercentage())
                .drawPercentage(openingModel.getDrawPercentage()).build();
    }
}
