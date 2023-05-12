package com.chat.messenger.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {

    private Integer id;
    private String username;
    private String message;
    private LocalDateTime timestamp;

    public MessageDto(Integer id, String username, String message, LocalDateTime timestamp) {
        this.id = id;
        this.username = username;
        this.message = message;
        this.timestamp = timestamp;
    }
}