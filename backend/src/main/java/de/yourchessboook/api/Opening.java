package de.yourchessboook.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Opening {
    private String name;
    private int numWins;
    private int numDraw;
    private int numLosses;
    private double winPercentage;
    private double drawPercentage;
}
