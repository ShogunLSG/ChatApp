package com.chat.messenger.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto {

    private Integer id;
    private String name;
    private LocalDateTime expiryTime;

    public EventDto() {
    }

    public EventDto(Integer id, String name, LocalDateTime expiryTime) {
        this.id = id;
        this.name = name;
        this.expiryTime = expiryTime;
    }
}
