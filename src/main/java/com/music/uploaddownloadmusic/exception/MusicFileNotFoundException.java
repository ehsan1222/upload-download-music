package com.music.uploaddownloadmusic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MusicFileNotFoundException extends RuntimeException{

    public MusicFileNotFoundException(String message) {
        super(message);
    }

    public MusicFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
