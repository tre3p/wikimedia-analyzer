package com.treep.streamproducer.config;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class Config {

    @Value("${wikimedia.url}")
    private String wikimediaStreamUrl;

    private final EventHandler wikimediaEventHandler;

    @Bean
    public EventSource wikimediaEventSource() throws URISyntaxException {
        log.debug("wikimediaEventSource()");
        return new EventSource.Builder(wikimediaEventHandler, new URI(wikimediaStreamUrl)).build();
    }
}
