package de.yourchessboook.config;

import de.yourchessboook.rest.lichess.LichessAPI;

import de.yourchessboook.rest.lichess.LichessClient;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LichessClientConfig {

    @Bean
    public LichessAPI getLichessAPI() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(LichessClient.class))
                .logLevel(Logger.Level.FULL)
                .target(LichessAPI.class, "https://lichess.org/api");
    }
}
