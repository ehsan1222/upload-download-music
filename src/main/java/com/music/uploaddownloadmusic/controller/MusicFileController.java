package com.music.uploaddownloadmusic.controller;

import com.music.uploaddownloadmusic.model.MusicFile;
import com.music.uploaddownloadmusic.payload.FirstPageMusicResponse;
import com.music.uploaddownloadmusic.service.MusicFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/musics")
public class MusicFileController {

    private MusicFileService musicFileService;

    @Autowired
    public MusicFileController(MusicFileService musicFileService) {
        this.musicFileService = musicFileService;
    }

    @GetMapping("/")
    public ResponseEntity<List<FirstPageMusicResponse>> getAllMusicsWithoutAudioFiles () {
        return null;
    }

    @GetMapping("/{musicId}")
    public ResponseEntity<MusicFile> getMusicFile(@PathVariable("musicId") Long musicId) {
        return null;
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
