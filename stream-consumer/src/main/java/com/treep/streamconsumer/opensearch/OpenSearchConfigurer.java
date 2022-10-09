package com.treep.streamconsumer.opensearch;

import lombok.extern.slf4j.Slf4j;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class OpenSearchConfigurer {

    private RestHighLevelClient openSearchClient;

    @Value("${opensearch.index}")
    private String wikimediaIndex;

    public OpenSearchConfigurer(RestHighLevelClient openSearchClient) {
        this.openSearchClient = openSearchClient;
    }

    public void configureOpenSearchIndices() throws IOException {
        log.info("+configureOpenSearchIndices(): configuring OpenSearch indices with index: {}", wikimediaIndex);
        if (openSearchClient.indices().exists(new GetIndexRequest(wikimediaIndex), RequestOptions.DEFAULT)) {
            log.info("-configureOpenSearchIndices(): index \"{}\" already exists.", wikimediaIndex);
            return;
        }
        CreateIndexRequest cir = new CreateIndexRequest(wikimediaIndex);
        openSearchClient.indices().create(cir, RequestOptions.DEFAULT);
        log.info("-configureOpenSearchIndices(): index \"{}\" successfully created!", wikimediaIndex);
    }
}
