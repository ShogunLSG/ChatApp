package com.chat.messenger.model.response;

import com.chat.messenger.model.dto.MessageDto;
import lombok.Data;

@Data
public class SendMessageResponse {

    private MessageDto messageDto;

    public SendMessageResponse(MessageDto messageDto) {
        this.messageDto = messageDto;
    }
}