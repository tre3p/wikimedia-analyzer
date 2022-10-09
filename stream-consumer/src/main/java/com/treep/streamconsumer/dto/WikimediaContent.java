package com.treep.streamconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WikimediaContent {

    private String domain;
    private boolean isBot;
}
