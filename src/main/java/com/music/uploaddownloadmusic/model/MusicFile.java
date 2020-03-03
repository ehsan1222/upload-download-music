package com.music.uploaddownloadmusic.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "music_file")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class MusicFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String fileName;

    @NonNull
    private String fileType;

    @Lob
    @NonNull
    private byte[] musicFile;

    @Lob
    @NonNull
    private byte[] musicFilePicture;
}
