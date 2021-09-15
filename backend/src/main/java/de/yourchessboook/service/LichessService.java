package de.yourchessboook.service;

import de.yourchessboook.model.GameEntity;
import de.yourchessboook.repo.GameRepository;
import de.yourchessboook.rest.lichess.LichessClient;
import de.yourchessboook.rest.lichess.LichessGameDto;
import de.yourchessboook.rest.lichess.LichessGamesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LichessService {
    private final LichessClient lichessClient;
    private final GameRepository gameRepository;

    @Autowired
    public LichessService(LichessClient lichessClient, GameRepository gameRepository) {
        this.lichessClient = lichessClient;
        this.gameRepository = gameRepository;
    }

    public LichessGamesDto getGames(String username) {
        LichessGamesDto lichessGamesDto = lichessClient.getGames(username);

        List<LichessGameDto> lichessGameDtoList = lichessGamesDto.getLichessGameDtos();
        if(lichessGameDtoList.isEmpty()){
            return lichessGamesDto;
        }

        add(lichessGameDtoList);

        return lichessGamesDto;
    }
    
    private void add(List<LichessGameDto> lichessGameDtoList){
        for (LichessGameDto lichessGameDto : lichessGameDtoList) {
            GameEntity gameEntity = new GameEntity();
            gameEntity.setOpening(lichessGameDto.getOpening().getName());
            gameEntity.setMoves(lichessGameDto.getMoves());
            gameRepository.save(gameEntity);
        }
    }
}
