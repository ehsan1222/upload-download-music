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
        Optional<MusicFile> musicFile = musicFileService.getMusicFile(musicId);
        if (musicFile.isPresent()) {
            return new ResponseEntity<>(musicFile.get(), HttpStatus.OK);
        }
        throw new MusicFileNotFoundException("musicId is NOT exists.");
    }

    @GetMapping("/search")
    public ResponseEntity<List<FirstPageMusicResponse>> getSearchedMusicFile(@RequestParam("musicName") String musicName) {
        return null;
    }

    @PutMapping("/{musicId}")
    public ResponseEntity<MusicFile> updateMusicFile(@PathVariable("musicId") Long musicId, @RequestBody MusicFile musicFile) {
        return null;
    }

    @DeleteMapping("/{musicId}")
    public ResponseEntity<?> deleteMusicFile(@PathVariable("musicId") Long musicId) {
        return null;
    }

}
