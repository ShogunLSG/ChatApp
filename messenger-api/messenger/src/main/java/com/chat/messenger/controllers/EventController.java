package com.chat.messenger.controllers;

import com.chat.messenger.model.ResponseMessage;
import com.chat.messenger.model.dto.EventDto;
import com.chat.messenger.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Get latest event")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Latest event",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EventDto.class)
                    )}
            )
    })
    @GetMapping(path = "")
    public ResponseEntity<EventDto> getLatestEvent(
    ) {
        log.info("Retrieving latest event");
        EventDto eventDto = eventService.getLatestEvent();
        log.info("Latest event retrieved");
        return ResponseEntity.ok(eventDto);
    }

    @Operation(summary = "Upload screen capture")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Result message",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseMessage.class)
                    )}
            )
    })
    @PostMapping(
            path = "/{username}/capture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResponseMessage> saveScreenCapture(
            @PathVariable String username,
            @RequestPart("attachment") MultipartFile multipartFile
    ) {
        log.info("Saving screen capture");
        eventService.saveScreenCapture(username, multipartFile);
        ResponseMessage responseMessage = new ResponseMessage(200, "Screen capture uploaded successfully");
        log.info("Screen capture saved");
        return ResponseEntity.ok(responseMessage);
    }
}
