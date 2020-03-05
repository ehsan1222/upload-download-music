package com.music.uploaddownloadmusic.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.ByteArrayResource;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AbstractMusicResponse {

    private Long id;
    private String fileName;
    private ByteArrayResource musicPictureResource;

}
