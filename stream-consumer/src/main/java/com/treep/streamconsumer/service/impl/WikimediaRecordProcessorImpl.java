package com.treep.streamconsumer.service.impl;

import com.treep.streamconsumer.dto.WikimediaContent;
import com.treep.streamconsumer.metrics.MetricsHolder;
import com.treep.streamconsumer.service.WikimediaRecordProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WikimediaRecordProcessorImpl implements WikimediaRecordProcessor {

    private final JSONParser jsonParser;
    private final MetricsHolder metricsHolder;

    @Override
    public void analyze(String content) {
        log.debug("+analyze()");
        WikimediaContent wikimediaContent;
        try {
            wikimediaContent = parse(content);
        } catch (ParseException e) {
            log.error("analyze(): error while parsing content from Kafka.");
            throw new RuntimeException(e);
        }

        String domain = wikimediaContent.getDomain();

        if (domain.contains("wikipedia")) {
            if (wikimediaContent.isBot()) {
                metricsHolder.botCounter.increment();
            }
            processDomainCountry(domain.split("\\."));
        }
        log.debug("-analyze()");
    }

    private WikimediaContent parse(String json) throws ParseException {
        log.debug("+parse()");
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

        boolean isBot = (boolean) jsonObject.get("bot");
        String domain = (String) jsonObject.get("server_name");

        log.debug("-parse()");
        return new WikimediaContent(domain, isBot);
    }

    private void processDomainCountry(String[] domainParts) {
        log.debug("+processDomainCountry()");
        if (domainParts.length == 3) {
            metricsHolder.allWikisCounter.increment();
            switch (domainParts[0]) {
                case "en" -> metricsHolder.enWikipediaCounter.increment();
                case "ru" -> metricsHolder.ruWikipediaCounter.increment();
                case "fr" -> metricsHolder.frWikipediaCounter.increment();
            }
        }
        log.debug("-processDomainCountry()");
    }
}
