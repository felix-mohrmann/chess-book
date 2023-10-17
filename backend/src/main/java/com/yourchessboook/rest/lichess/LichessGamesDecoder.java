package com.yourchessboook.rest.lichess;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.jackson.JacksonDecoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

public class LichessGamesDecoder extends JacksonDecoder {

    private final ObjectMapper mapper;

    public LichessGamesDecoder(ObjectMapper mapper) {
        this.mapper = mapper;
    }

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
        return mapper.treeToValue(jsonNode, LichessGameDto.class);
    }
}
