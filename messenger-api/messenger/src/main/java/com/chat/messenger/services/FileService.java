package com.chat.messenger.services;

import com.chat.messenger.exceptions.MessengerException;
import com.chat.messenger.model.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String addCapture(MultipartFile file, FileMetadata fileMetadata) throws MessengerException;
}
