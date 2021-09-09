package de.yourchessboook.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class LichessGame {

    private String id;
    private String status;
    private String opening;
    private String moves;
    private String players;
}
