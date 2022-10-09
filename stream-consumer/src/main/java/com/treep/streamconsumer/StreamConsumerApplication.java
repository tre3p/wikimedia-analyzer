package com.treep.streamconsumer;

import com.treep.streamconsumer.opensearch.OpenSearchConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class StreamConsumerApplication {

    @Autowired
    private OpenSearchConfigurer openSearchConfigurer;

    public static void main(String[] args) {
        SpringApplication.run(StreamConsumerApplication.class, args);
    }

    @PostConstruct
    public void configureOpenSearchIndices() throws IOException {
        openSearchConfigurer.configureOpenSearchIndices();
    }

}
