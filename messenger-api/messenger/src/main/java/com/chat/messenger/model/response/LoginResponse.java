package com.chat.messenger.model.response;

import com.chat.messenger.model.dto.UserDto;
import lombok.Data;

@Data
public class LoginResponse {

    private boolean successful;
    private String message;
    private UserDto user;

    public LoginResponse(boolean successful, String message, UserDto user) {
        this.successful = successful;
        this.message = message;
        this.user = user;
    }
}
