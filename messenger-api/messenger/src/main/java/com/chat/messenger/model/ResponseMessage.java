package com.chat.messenger.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage {

    private int code;
    private String message;

    public ResponseMessage() {
    }

    public ResponseMessage(String message) {
        this.code = 0;
        this.message = message;
    }

    public ResponseMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }
}