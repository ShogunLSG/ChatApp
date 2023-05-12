package com.chat.messenger.services;

import com.chat.messenger.model.dto.MessageDto;
import com.chat.messenger.model.request.SendMessageRequest;
import com.chat.messenger.model.response.SendMessageResponse;

import java.util.List;

public interface MessageService {

    List<MessageDto> getMessages();

    SendMessageResponse saveMessage(SendMessageRequest request);
}