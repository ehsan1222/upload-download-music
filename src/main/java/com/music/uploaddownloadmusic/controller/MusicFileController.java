package com.music.uploaddownloadmusic.controller;

import com.music.uploaddownloadmusic.exception.MusicFileNotFoundException;
import com.music.uploaddownloadmusic.exception.MusicFileStorageException;
import com.music.uploaddownloadmusic.exception.ParameterNotFoundException;
import com.music.uploaddownloadmusic.model.MusicFile;
import com.music.uploaddownloadmusic.payload.AbstractMusicResponse;
import com.music.uploaddownloadmusic.payload.RequestUploadMusic;
import com.music.uploaddownloadmusic.service.MusicFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public ResponseEntity<?> addMusicFile(@RequestBody RequestUploadMusic requestUploadMusic) {
        try {
            if (requestUploadMusic.getAudioFile() == null) {
                throw new ParameterNotFoundException("AudioFile Not Found");
            }
            if (requestUploadMusic.getPictureFile() == null) {
                throw new ParameterNotFoundException("PictureFile Not Found");
            }
            if (requestUploadMusic.getFileName() == null || requestUploadMusic.getFileName().trim().equals("")) {
                throw new ParameterNotFoundException("FileName Not Found");
            }
            // get file name
            String fileName = requestUploadMusic.getFileName();
            // get file extension
            String fileType = Objects.requireNonNull(requestUploadMusic.getAudioFile().getOriginalFilename()).
                    substring(requestUploadMusic.getAudioFile().getOriginalFilename().lastIndexOf(".") + 1);
            // Set requested parameter to Entity Class
            MusicFile musicFile = new MusicFile(fileName,
                    fileType,
                    requestUploadMusic.getAudioFile().getBytes(),
                    requestUploadMusic.getPictureFile().getBytes());
            // Save New Music in DB
            musicFileService.saveMusicFile(musicFile);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            throw new MusicFileStorageException("an error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<AbstractMusicResponse>> getAllMusicsWithoutAudioFiles() {
        // Get all music Records
        List<MusicFile> allMusics = musicFileService.getAllMusics();

        // There was't music file
        if (allMusics == null) {
            throw new MusicFileNotFoundException("Music File NOT Found!");
        }
        List<AbstractMusicResponse> abstractMusicRespons = new ArrayList<>();
        for (MusicFile musicFile : allMusics) {
            AbstractMusicResponse abstractMusicResponse = new AbstractMusicResponse
                    (musicFile.getId(), musicFile.getFileName(), new ByteArrayResource(musicFile.getMusicFilePicture()));
            abstractMusicRespons.add(abstractMusicResponse);
        }
        return new ResponseEntity<>(abstractMusicRespons, HttpStatus.OK);
    }

    @GetMapping("/{musicId}")
    public ResponseEntity<AbstractMusicResponse> getMusicFile(@PathVariable("musicId") Long musicId) {
        if (musicId == null) {
            throw new ParameterNotFoundException("MusicId Not Found");
        }
        // Get Music from DB
        Optional<MusicFile> musicFileOptional = musicFileService.getMusicFile(musicId);
        // Check musicId exists in DB
        if (musicFileOptional.isPresent()) {
            MusicFile musicFile = musicFileOptional.get();
            // Set response values
            AbstractMusicResponse abstractMusicResponse = new AbstractMusicResponse
                    (musicFile.getId(), musicFile.getFileName(), new ByteArrayResource(musicFile.getMusicFilePicture()));
            return new ResponseEntity<>(abstractMusicResponse, HttpStatus.OK);
        }
        throw new MusicFileNotFoundException("This musicId is NOT exist.");
    }

    @GetMapping("/{musicId:.+}/download")
    public ResponseEntity<Resource> getMusicAudioFile(@PathVariable("musicId") Long musicId) {
        if (musicId == null) {
            throw new ParameterNotFoundException("MusicId Not Found");
        }
        // Get Music from DB
        Optional<MusicFile> musicFileOptional = musicFileService.getMusicFile(musicId);
        // Check musicId exists in DB
        if (musicFileOptional.isPresent()) {
            MusicFile musicFile = musicFileOptional.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(musicFile.getFileType()))
                    .body(new ByteArrayResource(musicFile.getMusicFile()));

        }
        throw new MusicFileNotFoundException("This musicId is NOT exist.");
    }

    @GetMapping("/search/{fileName}")
    public ResponseEntity<List<AbstractMusicResponse>> getSearchedMusicFile(@PathVariable("fileName") String fileName) {
        List<MusicFile> musicFiles = musicFileService.getSearchedMusicFilesByFileName(fileName);
        if (musicFiles == null) {
            throw new MusicFileNotFoundException("This fileName is NOT exist.");
        }
        List<AbstractMusicResponse> musicResponses = new ArrayList<>();
        for (MusicFile musicFile : musicFiles) {
            AbstractMusicResponse abstractMusicResponse = new AbstractMusicResponse
                    (musicFile.getId(), musicFile.getFileName(), new ByteArrayResource(musicFile.getMusicFilePicture()));
            musicResponses.add(abstractMusicResponse);
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
