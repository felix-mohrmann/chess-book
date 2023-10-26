package de.yourchessboook.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.yourchessboook.rest.lichess.*;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LichessClientConfig {

    private final ObjectMapper mapper;

    @Bean
    public LichessAPI getLichessAPI() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new LichessGamesDecoder(mapper))
                .logger(new Slf4jLogger(LichessClient.class))
                .logLevel(Logger.Level.FULL)
                .target(LichessAPI.class, "https://lichess.org/api");
    }
}
