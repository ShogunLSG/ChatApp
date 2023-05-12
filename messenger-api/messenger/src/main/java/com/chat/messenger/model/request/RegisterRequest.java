package com.chat.messenger.model.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}
