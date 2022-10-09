package com.treep.streamconsumer.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class Config {

    @Value("${opensearch.url}")
    private String openSearchUrl;

    @Bean
    public RestHighLevelClient openSearchClient() {
        RestHighLevelClient restHighLevelClient;
        URI connectionURI = URI.create(openSearchUrl);

        String userInfo = connectionURI.getUserInfo();

        if (userInfo == null) {
            restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost(connectionURI.getHost(), connectionURI.getPort(), "http")));
        } else {
            String[] authData = userInfo.split(":");

            CredentialsProvider cp = new BasicCredentialsProvider();
            cp.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(authData[0], authData[1]));

            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(new HttpHost(connectionURI.getHost(), connectionURI.getPort(), connectionURI.getScheme()))
                            .setHttpClientConfigCallback(
                                    httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(cp)
                                            .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                            )
            );

        }
        return restHighLevelClient;
    }
}
