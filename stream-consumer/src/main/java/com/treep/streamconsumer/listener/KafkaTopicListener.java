package com.treep.streamconsumer.listener;

import com.treep.streamconsumer.dto.WikimediaContent;
import com.treep.streamconsumer.service.WikimediaRecordProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaTopicListener {

    @Autowired
    private WikimediaRecordProcessor wikimediaRecordProcessor;

    @KafkaListener(topics = "${spring.kafka.topic}")
    public void queueListener(ConsumerRecord<String, String> consumerRecord) throws ParseException {
        log.info("+queueListener(): received a new message. Processing now..");
        WikimediaContent content = wikimediaRecordProcessor.parse(consumerRecord.value());
        wikimediaRecordProcessor.analyze(content);
        log.info("-queueListener(): a record has been processed");
    }
}
