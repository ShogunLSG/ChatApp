package com.chat.messenger.services;

import com.chat.messenger.model.dto.EventDto;
import org.springframework.web.multipart.MultipartFile;

public interface EventService {

    EventDto getLatestEvent();

    void publishScreenCaptureEvent();

    void saveScreenCapture(String username, MultipartFile multipartFile);
}
