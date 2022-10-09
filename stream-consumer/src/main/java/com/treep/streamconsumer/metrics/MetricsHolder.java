package com.treep.streamconsumer.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MetricsHolder {

    public Counter botCounter;

    public Counter allWikisCounter;
    public Counter ruWikipediaCounter;
    public Counter frWikipediaCounter;
    public Counter enWikipediaCounter;

    public MetricsHolder(MeterRegistry registry) {
        botCounter = registry.counter("bot_counter");

        allWikisCounter = registry.counter("all_wikis");
        ruWikipediaCounter = registry.counter("ru_wiki");
        frWikipediaCounter = registry.counter("fr_wikipedia");
        enWikipediaCounter = registry.counter("en_wikipedia");
    }
}
