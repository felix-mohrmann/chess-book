package de.yourchessboook.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.yourchessboook.rest.lichess.LichessAPI;
import de.yourchessboook.rest.lichess.LichessClient;
import de.yourchessboook.rest.lichess.LichessGameDto;
import de.yourchessboook.rest.lichess.LichessGamesDto;
import feign.Feign;
import feign.Logger;
import feign.Response;
import feign.Util;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

@Configuration
public class LichessClientConfig {

    @Bean
    public LichessAPI getLichessAPI() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new LichessGamesDecoder())
                .logger(new Slf4jLogger(LichessClient.class))
                .logLevel(Logger.Level.FULL)
                .target(LichessAPI.class, "https://lichess.org/api");
    }

    static class LichessGamesDecoder extends JacksonDecoder {

        @Override
        public Object decode(Response response, Type type) throws IOException {
            Reader reader = response.body().asReader(Util.UTF_8);
            LichessGamesDto lichessGamesDto = new LichessGamesDto();

            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            while (line != null) {
                ObjectMapper objectMapper = new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                JsonParser jsonParser = objectMapper.getFactory().createParser(line);
                LichessGameDto lichessGameDto = deserialize(jsonParser);
                lichessGamesDto.addLichessGameDto(lichessGameDto);

                line = bufferedReader.readLine();
            }
            return lichessGamesDto;
        }

        private LichessGameDto deserialize(JsonParser jsonParser) throws IOException {
            JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

            String id = jsonNode.get("id").asText();
            String moves = jsonNode.get("moves").asText();
            String status = jsonNode.get("status").asText();

            // todo map missing properties here

            LichessGameDto lichessGameDto = new LichessGameDto();
            lichessGameDto.setId(id);
            lichessGameDto.setMoves(moves);
            lichessGameDto.setStatus(status);
            return lichessGameDto;
        }
    }
}
