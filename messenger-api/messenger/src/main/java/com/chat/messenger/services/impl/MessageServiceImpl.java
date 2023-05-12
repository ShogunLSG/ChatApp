package com.chat.messenger.services.impl;

import com.chat.messenger.entities.Message;
import com.chat.messenger.model.dto.MessageDto;
import com.chat.messenger.model.request.SendMessageRequest;
import com.chat.messenger.model.response.SendMessageResponse;
import com.chat.messenger.repos.MessageRepository;
import com.chat.messenger.services.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<MessageDto> getMessages() {
        return messageRepository.findAll()
                .stream()
                .map(this::buildMessageDto)
                .collect(Collectors.toList());
    }

    @Override
    public SendMessageResponse saveMessage(SendMessageRequest request) {
        Message message = new Message();
        message.setUsername(request.getUsername());
        message.setMessage(request.getMessage());
        message.setTimestamp(LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);
        return new SendMessageResponse(buildMessageDto(savedMessage));
    }

    private MessageDto buildMessageDto(Message message) {
        return new MessageDto(
                message.getId(),
                message.getUsername().toLowerCase(),
                message.getMessage(),
                message.getTimestamp()
        );
    }
}