package com.treep.streamconsumer.service.impl;

import com.treep.streamconsumer.dto.WikimediaContent;
import com.treep.streamconsumer.metrics.MetricsHolder;
import com.treep.streamconsumer.service.WikimediaRecordProcessor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class WikimediaRecordProcessorImpl implements WikimediaRecordProcessor {

    private JSONParser jsonParser;
    private MetricsHolder metricsHolder;

    public WikimediaRecordProcessorImpl(JSONParser jsonParser, MetricsHolder metricsHolder) {
        this.jsonParser = jsonParser;
        this.metricsHolder = metricsHolder;
    }

    @Override
    public WikimediaContent parse(String json) throws ParseException {
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

        boolean isBot = (boolean) jsonObject.get("bot");
        String domain = (String) jsonObject.get("server_name");

        return new WikimediaContent(domain, isBot);
    }

    @Override
    public void analyze(WikimediaContent content) {
        if (content.isBot()) {
            metricsHolder.botCounter.increment();
        }

        String domain = content.getDomain();

        if (domain.contains("wikipedia")) {
            processDomainCountry(domain.split("\\."));
        }
    }

    private void processDomainCountry(String[] domainParts) {
        if (domainParts.length == 3) {
            metricsHolder.allWikisCounter.increment();
            switch (domainParts[0]) {
                case "en" -> metricsHolder.enWikipediaCounter.increment();
                case "ru" -> metricsHolder.ruWikipediaCounter.increment();
                case "fr" -> metricsHolder.frWikipediaCounter.increment();
            }
        }
    }
}
