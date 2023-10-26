package de.yourchessboook.rest.lichess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LichessPlayersDto {
    private LichessColorDto white;
    private LichessColorDto black;
}
