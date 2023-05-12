package com.chat.messenger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties("messenger.media")
@Data
@Validated
public class MediaConfig {
    private String capturePath;
}