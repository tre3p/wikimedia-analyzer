package com.treep.streamproducer.config;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class Config {

    @Value("${wikimedia.url}")
    private String wikimediaStreamUrl;

    private EventHandler wikimediaEventHandler;

    public Config(EventHandler wikimediaEventHandler) {
        this.wikimediaEventHandler = wikimediaEventHandler;
    }

    @Bean
    public EventSource wikimediaEventSource() throws URISyntaxException {
        return new EventSource.Builder(wikimediaEventHandler, new URI(wikimediaStreamUrl)).build();
    }
}
