package com.yourchessboook.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
public class LichessGames {
    List<LichessGame> lichessGames = new LinkedList<>();

    public void addLichessGame(LichessGame lichessGame) {
        this.lichessGames.add(lichessGame);
    }
}
