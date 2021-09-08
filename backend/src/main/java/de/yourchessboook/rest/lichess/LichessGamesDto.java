package de.yourchessboook.rest.lichess;

import java.util.LinkedList;
import java.util.List;

public class LichessGamesDto {

    List<LichessGameDto> lichessGameDtos = new LinkedList<>();

    public void addLichessGameDtos(List<LichessGameDto> lichessGameDtos) {
        this.lichessGameDtos.addAll(new LinkedList<>(lichessGameDtos));
    }
}
