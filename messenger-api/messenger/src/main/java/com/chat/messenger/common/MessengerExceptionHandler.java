package com.chat.messenger.common;

import com.chat.messenger.exceptions.MessengerException;
import com.chat.messenger.model.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@Slf4j
@ControllerAdvice
public final class MessengerExceptionHandler {

    @ExceptionHandler(value = MessengerException.class)
    public ResponseEntity<?> handleException(MessengerException e) {
        log.error(Arrays.toString(e.getStackTrace()), e);
        return ResponseEntity.status(500).body(new ResponseMessage(e.getMessage()));
    }
}