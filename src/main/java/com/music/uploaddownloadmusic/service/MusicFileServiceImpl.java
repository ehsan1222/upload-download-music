package com.music.uploaddownloadmusic.service;

import com.music.uploaddownloadmusic.model.MusicFile;
import com.music.uploaddownloadmusic.repository.MusicFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MusicFileServiceImpl implements MusicFileService {

    @Autowired
    private MusicFileRepository musicFileRepository;


    @Override
    @Transactional
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
    @Transactional
    public List<MusicFile> getSearchedMusicFilesByFileName(String fileName) {
        return musicFileRepository.findByFileNameContaining(fileName);
    }

    @Override
    @Transactional
    public MusicFile updateMusicFile(MusicFile musicFile) {
        return musicFileRepository.save(musicFile);
    }

    @Override
    @Transactional
    public void deleteMusicFile(Long musicId) {
        musicFileRepository.deleteById(musicId);
    }
}
