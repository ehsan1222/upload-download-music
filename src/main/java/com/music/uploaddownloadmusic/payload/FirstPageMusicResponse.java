package com.music.uploaddownloadmusic.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FirstPageMusicResponse {

    private Long id;
    private String fileName;
    private byte[] musicFilePicture;

}
