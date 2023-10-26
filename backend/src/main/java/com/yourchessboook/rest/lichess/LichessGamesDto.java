package de.yourchessboook.rest.lichess;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class LichessGamesDto {

    List<LichessGameDto> lichessGameDtos = new LinkedList<>();

    public void addLichessGameDto(LichessGameDto lichessGameDto) {
        this.lichessGameDtos.add(lichessGameDto);
    }

    @JsonIgnore
    public Map<String, LichessGameDto> toMap() {
        Map<String, LichessGameDto> lichessGameDtoMap = new HashMap<>();
        if (lichessGameDtos != null) {
            for (LichessGameDto lichessGameDto : lichessGameDtos) {
                lichessGameDtoMap.put(lichessGameDto.getId(), lichessGameDto);
            }
        }
        return lichessGameDtoMap;
    }
}
