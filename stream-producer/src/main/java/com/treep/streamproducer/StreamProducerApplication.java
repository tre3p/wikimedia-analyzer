package com.treep.streamproducer;

import com.launchdarkly.eventsource.EventSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

import javax.annotation.PostConstruct;

@Slf4j
@EnableKafka
@SpringBootApplication
public class StreamProducerApplication {

    @Autowired
    private EventSource eventSource;

    public static void main(String[] args) {
        SpringApplication.run(StreamProducerApplication.class, args);
    }

    @PostConstruct
    private void runEventSource() {
        log.debug("+runEventSource(): starting event source..");
        eventSource.start();
        log.debug("-runEventSource(): event source started!");
    }

}
