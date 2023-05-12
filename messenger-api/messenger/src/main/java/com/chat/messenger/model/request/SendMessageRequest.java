package com.chat.messenger.model.request;

import lombok.Data;

@Data
public class SendMessageRequest {
    private String username;
    private String message;
}