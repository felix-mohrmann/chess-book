package com.yourchessboook.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yourchessboook.rest.lichess.LichessGameDto;
import com.yourchessboook.rest.lichess.LichessGamesDecoder;
import com.yourchessboook.rest.lichess.LichessGamesDto;
import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import static feign.Request.HttpMethod.GET;
import static feign.Request.create;
import static java.nio.charset.Charset.defaultCharset;
import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LichessGamesDecodeTest {

    @Test
    public void testDecode() throws IOException, URISyntaxException {
        File resourceFile = getLichessNdJsonFile();
        byte[] jsonData = FileUtils.readFileToByteArray(resourceFile);

        Request request = create(GET, "", emptyMap(), new byte[] {}, defaultCharset(), new RequestTemplate());

        Response response = Response.builder()
                .request(request)
                .body(jsonData)
                .status(HttpServletResponse.SC_OK)
                .headers(emptyMap())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        LichessGamesDecoder lichessGamesDecoder = new LichessGamesDecoder(mapper);
        Object object = lichessGamesDecoder.decode(response, LichessGamesDto.class);
        assertTrue(object instanceof LichessGamesDto);

        LichessGamesDto lichessGamesDto = (LichessGamesDto) object;

        Map<String, LichessGameDto> lichessGameDtoMap = lichessGamesDto.toMap();
        assertEquals(5, lichessGameDtoMap.size());

        assertNotNull(lichessGameDtoMap.get("jtAqZmoG"));
        assertNotNull(lichessGameDtoMap.get("DVEA7Avl"));
        assertNotNull(lichessGameDtoMap.get("SNS3xwSc"));
        assertNotNull(lichessGameDtoMap.get("NjLTEBKh"));
        assertNotNull(lichessGameDtoMap.get("C2KM5Bgf"));
    }

    private File getLichessNdJsonFile() throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("lichessgames.ndjson");
        assertNotNull(resource);
        return new File(resource.toURI());
    }

}
