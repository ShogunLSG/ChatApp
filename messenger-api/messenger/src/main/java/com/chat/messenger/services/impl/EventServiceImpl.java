package com.chat.messenger.services.impl;

import com.chat.messenger.entities.Event;
import com.chat.messenger.exceptions.MessengerException;
import com.chat.messenger.model.FileMetadata;
import com.chat.messenger.model.dto.EventDto;
import com.chat.messenger.repos.EventRepository;
import com.chat.messenger.services.EventService;
import com.chat.messenger.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final FileService fileService;

    public EventServiceImpl(EventRepository eventRepository, FileService fileService) {
        this.eventRepository = eventRepository;
        this.fileService = fileService;
    }

    @Override
    public EventDto getLatestEvent() {
        Event event = eventRepository.findAll().stream().findFirst().orElse(null);
        return event != null ? new EventDto(event.getId(), event.getName(), event.getExpiryTime()) : new EventDto();
    }

    @Override
    public void publishScreenCaptureEvent() {
        Event event = new Event();
        event.setName("screen_capture");
        event.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        eventRepository.save(event);
    }

    @Override
    public void saveScreenCapture(String username, MultipartFile multipartFile) {
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setUsername(username);
        fileMetadata.setType(multipartFile.getContentType());
        fileService.addCapture(multipartFile, fileMetadata);
    }
}