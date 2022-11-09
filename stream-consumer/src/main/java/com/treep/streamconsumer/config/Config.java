package com.treep.streamconsumer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class Config {

    @Bean
    public ObjectMapper objectMapper() {
        log.debug("objectMapper()");
        return new ObjectMapper();
    }

    @Bean
    public JSONParser jsonParser() {
        log.debug("jsonParser()");
        return new JSONParser();
    }
}
