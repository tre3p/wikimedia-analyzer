package com.treep.streamproducer.handlers;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WikimediaChangeHandler implements EventHandler {

    private KafkaTemplate<String, String> wikimediaKafkaProducer;

    @Value("${wikimedia.topic.name}")
    private String topicName;

    public WikimediaChangeHandler(KafkaTemplate<String, String> wikimediaKafkaProducer) {
        this.wikimediaKafkaProducer = wikimediaKafkaProducer;
    }

    @Override
    public void onOpen() {
        // nothing to do
    }

    @Override
    public void onClosed() {
        // nothing to do
    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) {
        log.info("+onMessage(): received new message from stream. Sending to Kafka..");
        wikimediaKafkaProducer.send(topicName, messageEvent.getData());
        log.info("-onMessage(): message successfully sent!");
    }

    @Override
    public void onComment(String s) {
        // nothing to do
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("onError(): error while reading stream: ", throwable);
    }
}
