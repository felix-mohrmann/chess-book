package de.yourchessboook.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Opening {
    private String name;
    private int numWins = 0;
    private int numDraw = 0;
    private int numLosses = 0;

    public void win(){
        numWins++;
    }

    public void loose(){
        numLosses++;
    }

    public void draw(){
        numDraw++;
    }

    public double getWinningPercentage() {
        return getNumWins()/getTotalGames();
    }

    public double getDrawPercentage() {
        return getNumDraw()/getTotalGames();
    }

    public double getTotalGames(){
        return getNumWins() + getNumDraw() + getNumLosses();
    }
}
