package com.music.uploaddownloadmusic.repository;

import com.music.uploaddownloadmusic.model.MusicFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicFileRepository extends JpaRepository<MusicFile, Long> {

}
