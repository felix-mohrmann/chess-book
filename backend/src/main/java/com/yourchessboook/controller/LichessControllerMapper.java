package com.yourchessboook.controller;

import java.util.List;

import com.yourchessboook.api.LichessGame;
import com.yourchessboook.api.LichessGames;
import com.yourchessboook.api.Opening;
import com.yourchessboook.api.Openings;
import com.yourchessboook.model.OpeningModel;
import com.yourchessboook.rest.lichess.LichessGameDto;
import com.yourchessboook.rest.lichess.LichessGamesDto;

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
                .players(new String[] { lichessGameDto.getPlayers().getWhite().getUser().getName(),
                        lichessGameDto.getPlayers().getBlack().getUser().getName() })
                .build();
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
                .fen(openingModel.getFen())
                .numWins(openingModel.getNumWins())
                .numDraw(openingModel.getNumDraw())
                .numLosses(openingModel.getNumLosses())
                .totalGames(openingModel.getTotalGames())
                .winPercentage(openingModel.getWinningPercentage())
                .drawPercentage(openingModel.getDrawPercentage()).build();
    }
}
