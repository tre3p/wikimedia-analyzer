package com.treep.streamconsumer.service;

import com.treep.streamconsumer.dto.WikimediaContent;
import org.json.simple.parser.ParseException;

public interface WikimediaRecordProcessor {
    WikimediaContent parse(String json) throws ParseException;
    void analyze(WikimediaContent content);
}
