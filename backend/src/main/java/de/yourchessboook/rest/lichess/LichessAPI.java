package de.yourchessboook.rest.lichess;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

import static de.yourchessboook.rest.lichess.LichessAPI.ACCESS_TOKEN_PARAM;

@Headers(
        {
                "Authorization: Bearer {" + ACCESS_TOKEN_PARAM + "}",
                "Content-Type: application/x-ndjson",
                "Accept: application/x-ndjson"
        }
)
public interface LichessAPI {
    String ACCESS_TOKEN_PARAM = "access-token";

    @RequestLine("GET /games/user/{name}?perfType=blitz&rated=true&opening=true&max=200")
    LichessGamesDto getGames(@Param(ACCESS_TOKEN_PARAM) String accessToken, @Param("name") String username);
}
