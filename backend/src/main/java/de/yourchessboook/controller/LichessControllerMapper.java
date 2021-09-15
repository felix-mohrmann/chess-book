package de.yourchessboook.controller;

import de.yourchessboook.api.LichessGame;
import de.yourchessboook.api.LichessGames;
import de.yourchessboook.rest.lichess.LichessGameDto;
import de.yourchessboook.rest.lichess.LichessGamesDto;

import java.util.List;

public class LichessControllerMapper {

    public LichessGames map(LichessGamesDto lichessGamesDto){
        LichessGames lichessGames = new LichessGames();
        List<LichessGameDto> lichessGameDtos = lichessGamesDto.getLichessGameDtos();
        for (LichessGameDto lichessGameDto : lichessGameDtos) {
            LichessGame lichessGame = map(lichessGameDto);
            lichessGames.addLichessGame(lichessGame);
        }
        return lichessGames;
    }

    public LichessGame map(LichessGameDto lichessGameDto){
        return LichessGame.builder()
                .id(lichessGameDto.getId())
                .moves(lichessGameDto.getMoves())
                .opening(lichessGameDto.getOpening().getName())
                .status(lichessGameDto.getStatus()).build();
                //.players(lichessGameDto.getPlayers()).build();
    }
}
