package com.music.uploaddownloadmusic.controller;

import com.music.uploaddownloadmusic.exception.MusicFileNotFoundException;
import com.music.uploaddownloadmusic.model.MusicFile;
import com.music.uploaddownloadmusic.payload.FirstPageMusicResponse;
import com.music.uploaddownloadmusic.service.MusicFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/musics")
public class MusicFileController {

    private MusicFileService musicFileService;

    @Autowired
    public MusicFileController(MusicFileService musicFileService) {
        this.musicFileService = musicFileService;
    }

    @PostMapping("/")
    public ResponseEntity<?> addMusicFile(@RequestBody MusicFile musicFile) {
        musicFileService.saveMusicFile(musicFile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<FirstPageMusicResponse>> getAllMusicsWithoutAudioFiles() {
        // Get all music Records
        List<MusicFile> allMusics = musicFileService.getAllMusics();

        // There was't music file
        if (allMusics == null) {
            throw new MusicFileNotFoundException("Music File NOT Found!");
        }
        List<FirstPageMusicResponse> firstPageMusicResponses = new ArrayList<>();
        for (MusicFile musicFile : allMusics) {
            FirstPageMusicResponse firstPageMusicResponse = new FirstPageMusicResponse
                    (musicFile.getId(), musicFile.getFileName(), musicFile.getMusicFilePicture());
            firstPageMusicResponses.add(firstPageMusicResponse);
        }
        return new ResponseEntity<>(firstPageMusicResponses, HttpStatus.OK);
    }

    @GetMapping("/{musicId}")
    public ResponseEntity<MusicFile> getMusicFile(@PathVariable("musicId") Long musicId) {
        Optional<MusicFile> musicFileOptional = musicFileService.getMusicFile(musicId);
        if (musicFileOptional.isPresent()) {
            return new ResponseEntity<>(musicFileOptional.get(), HttpStatus.OK);
        }
        throw new MusicFileNotFoundException("This musicId is NOT exist.");
    }

    @GetMapping("/search/{fileName}")
    public ResponseEntity<List<FirstPageMusicResponse>> getSearchedMusicFile(@PathVariable("fileName") String fileName) {
        List<MusicFile> musicFiles = musicFileService.getSearchedMusicFilesByFileName(fileName);
        if (musicFiles == null) {
            throw new MusicFileNotFoundException("This fileName is NOT exist.");
        }
        List<FirstPageMusicResponse> musicResponses = new ArrayList<>();
        for (MusicFile musicFile: musicFiles) {
            FirstPageMusicResponse firstPageMusicResponse = new FirstPageMusicResponse
                    (musicFile.getId(), musicFile.getFileName(), musicFile.getMusicFilePicture());
            musicResponses.add(firstPageMusicResponse);
        }
        return new ResponseEntity<>(musicResponses, HttpStatus.OK);
    }

    @PutMapping("/{musicId}")
    public ResponseEntity<MusicFile> updateMusicFile(@PathVariable("musicId") Long musicId, @RequestBody MusicFile musicFile) {
        Optional<MusicFile> musicFileOptional = musicFileService.getMusicFile(musicId);
        if (musicFileOptional.isPresent()) {
            MusicFile resMusicFile = musicFileOptional.get();
            resMusicFile.setFileName(musicFile.getFileName());
            resMusicFile.setFileType(musicFile.getFileType());
            resMusicFile.setMusicFile(musicFile.getMusicFile());
            resMusicFile.setMusicFilePicture(musicFile.getMusicFilePicture());
            return new ResponseEntity<>(resMusicFile, HttpStatus.OK);
        }
        throw new MusicFileNotFoundException("This musicId is NOT exist.");
    }

    @DeleteMapping("/{musicId}")
    public ResponseEntity<?> deleteMusicFile(@PathVariable("musicId") Long musicId) {
        Optional<MusicFile> musicFileOptional = musicFileService.getMusicFile(musicId);
        if (musicFileOptional.isPresent()) {
            musicFileService.deleteMusicFile(musicId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        throw new MusicFileNotFoundException("This musicId is NOT exist.");
    }

}
