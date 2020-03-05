package com.music.uploaddownloadmusic.exception;

public class MusicFileStorageException extends RuntimeException {
    public MusicFileStorageException(String message) {
        super(message);
    }

    public MusicFileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
