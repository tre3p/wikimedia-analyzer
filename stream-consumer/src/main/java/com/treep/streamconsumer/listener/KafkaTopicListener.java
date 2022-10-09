package com.treep.streamconsumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.index.IndexResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class KafkaTopicListener {

    @Value("${opensearch.index}")
    private String openSearchIndex;

    private RestHighLevelClient openSearchClient;

    public KafkaTopicListener(RestHighLevelClient openSearchClient) {
        this.openSearchClient = openSearchClient;
    }

    @KafkaListener(topics = "${spring.kafka.topic}")
    public void queueListener(ConsumerRecord<String, String> consumerRecord) throws IOException {
        log.info("+queueListener(): received a new message. Sending to OpenSearch now..");
        IndexRequest indexRequest = new IndexRequest(openSearchIndex)
                .source(consumerRecord.value(), XContentType.JSON);

        IndexResponse indexResponse = openSearchClient.index(indexRequest, RequestOptions.DEFAULT);
        log.info("-queueListener(): a record with ID: \"{}\" has been successfully sent to OpenSearch.", indexResponse.getId());
    }
}
