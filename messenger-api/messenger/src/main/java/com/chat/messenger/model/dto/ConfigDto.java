package com.chat.messenger.model.dto;

import lombok.Data;

@Data
public class ConfigDto {

    private Boolean clientEnabled;

    public ConfigDto(Boolean clientEnabled) {
        this.clientEnabled = clientEnabled;
    }
}
