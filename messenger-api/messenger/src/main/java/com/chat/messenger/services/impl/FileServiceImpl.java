package com.chat.messenger.services.impl;

import com.chat.messenger.config.MediaConfig;
import com.chat.messenger.exceptions.MessengerException;
import com.chat.messenger.model.FileMetadata;
import com.chat.messenger.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    private final MediaConfig mediaConfig;

    public FileServiceImpl(MediaConfig mediaConfig) {
        this.mediaConfig = mediaConfig;
    }

    @Override
    public String addCapture(MultipartFile multipartFile, FileMetadata fileMetadata) throws MessengerException {
        String fileName = fileMetadata.getUsername() + "-" + System.currentTimeMillis() + "." + getExtension(multipartFile);
        File dest = new File(mediaConfig.getCapturePath(), fileName);
        dest.mkdirs();
        try {
            multipartFile.transferTo(dest);
        } catch (IOException e) {
            throw new MessengerException("Error saving photo", e);
        }
        return fileName;
    }

    private String getExtension(MultipartFile multipartFile) {
        return getExtension(multipartFile.getOriginalFilename());
    }

    private String getExtension(String filename) {
        filename = filename.toLowerCase();
        int lastIndexOf = filename.lastIndexOf(46);
        if (lastIndexOf == -1) {
            return "";
        }
        return filename.substring(lastIndexOf + 1);
    }
}