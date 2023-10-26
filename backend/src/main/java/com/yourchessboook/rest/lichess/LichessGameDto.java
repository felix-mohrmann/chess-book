package de.yourchessboook.rest.lichess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LichessGameDto {
    private String id;
    private String status;
    private LichessOpeningDto opening;
    private String moves;
    private LichessPlayersDto players;
    private String winner;
}
