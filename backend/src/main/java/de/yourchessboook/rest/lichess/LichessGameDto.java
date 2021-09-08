package de.yourchessboook.rest.lichess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LichessGameDto {
    private String status;
    private String players;
    private String opening;
    private String moves;
}
