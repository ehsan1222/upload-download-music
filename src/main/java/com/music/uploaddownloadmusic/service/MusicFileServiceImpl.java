package com.music.uploaddownloadmusic.service;

import com.music.uploaddownloadmusic.model.MusicFile;
import com.music.uploaddownloadmusic.repository.MusicFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MusicFileServiceImpl implements MusicFileService {

    @Autowired
    private MusicFileRepository musicFileRepository;


    @Override
    public MusicFile saveMusicFile(MusicFile musicFile) {
        return musicFileRepository.save(musicFile);
    }

    @Override
    public List<MusicFile> getAllMusics() {
        return musicFileRepository.findAll();
    }

    @Override
    public Optional<MusicFile> getMusicFile(Long musicId) {
        return musicFileRepository.findById(musicId);
    }

    @Override
    public MusicFile updateMusicFile(MusicFile musicFile) {
        return musicFileRepository.save(musicFile);
    }

    @Override
    public void deleteMusicFile(Long musicId) {
        musicFileRepository.deleteById(musicId);
    }
}
