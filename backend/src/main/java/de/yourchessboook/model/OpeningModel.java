package de.yourchessboook.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OpeningModel {
    private String name;
    private String fen;
    private int numWins = 0;
    private int numDraw = 0;
    private int numLosses = 0;

    public void win() {
        numWins++;
    }

    public void loose() {
        numLosses++;
    }

    public void draw() {
        numDraw++;
    }

    public double getWinningPercentage() {
        return Math.round((double) getNumWins() / getTotalGames() * 100);
    }

    public double getDrawPercentage() {
        return Math.round((double) getNumDraw() / getTotalGames() * 100);
    }

    public int getTotalGames() {
        return getNumWins() + getNumDraw() + getNumLosses();
    }
}
