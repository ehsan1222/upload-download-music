package com.music.uploaddownloadmusic.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestUploadMusic {
    private String fileName;
    private MultipartFile audioFile;
    private MultipartFile pictureFile;
}
