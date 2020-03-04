package com.music.uploaddownloadmusic.service;

import com.music.uploaddownloadmusic.model.MusicFile;

import java.util.List;

public interface MusicFileService {

    void saveMusicFile(MusicFile musicFile);

    List<MusicFile> getAllMusics();

    MusicFile getMusicFile(Long musicId);

    MusicFile updateMusicFile(MusicFile musicFile);

    void deleteMusicFile(Long musicId);

}
