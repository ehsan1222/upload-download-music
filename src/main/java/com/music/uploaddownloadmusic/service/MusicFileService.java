package com.music.uploaddownloadmusic.service;

import com.music.uploaddownloadmusic.model.MusicFile;

import java.util.List;
import java.util.Optional;

public interface MusicFileService {

    MusicFile saveMusicFile(MusicFile musicFile);

    List<MusicFile> getAllMusics();

    Optional<MusicFile> getMusicFile(Long musicId);

    MusicFile updateMusicFile(MusicFile musicFile);

    void deleteMusicFile(Long musicId);

}
