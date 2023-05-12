package com.chat.messenger.messenger.controllers;

import com.chat.messenger.model.dto.MessageDto;
import com.chat.messenger.model.request.SendMessageRequest;
import com.chat.messenger.model.response.SendMessageResponse;
import com.chat.messenger.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Get all chat messages")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Messages retrieved",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = MessageDto.class, type = "List"))
                    )})
    })
    @GetMapping(path = "")
    public ResponseEntity<List<MessageDto>> getChatMessages() {
        log.info("Retrieving messages");
        List<MessageDto> messages = messageService.getMessages();
        log.info("Retrieved {} messages", messages.size());
        return ResponseEntity.ok(messages);
    }

    @Operation(summary = "Send a chat message")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Message sent",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SendMessageResponse.class)
                    )})
    })
    @PostMapping(path = "")
    public ResponseEntity<SendMessageResponse> sendMessage(@RequestBody SendMessageRequest request) {
        log.info("Sending messages");
        SendMessageResponse response = messageService.saveMessage(request);
        log.info("Message sent");
        return ResponseEntity.ok(response);
    }
}
