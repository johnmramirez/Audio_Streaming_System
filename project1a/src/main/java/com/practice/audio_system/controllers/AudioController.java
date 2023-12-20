package com.practice.audio_system.controllers;

import com.practice.audio_system.dto.Audio;
import com.practice.audio_system.services.AudioService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class AudioController {

    @Autowired
    private AudioService audioService;

    @GetMapping("/tracks/{id}")
    public ResponseEntity getAudio(@PathVariable String id) throws IOException {
        Audio audio = this.audioService.getAudio(id);
        if (audio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().header(HttpHeaders.LOCATION, "/tracks/stream/" + id).build();
    }

    @GetMapping("/tracks/stream/{id}")
    public void streamAudio(@PathVariable String id, HttpServletResponse response) throws IOException {
        Audio audio = this.audioService.getAudio(id);
        FileCopyUtils.copy(audio.getStream(), response.getOutputStream());
    }

    @PostMapping("/tracks")
    public ResponseEntity addAudio(@RequestParam("title") String title, @RequestParam("file") MultipartFile file) {
        ResponseEntity response = null;
        try {
            String id = this.audioService.putAudio(title, file);
            response = ResponseEntity.ok().header(HttpHeaders.LOCATION, "/tracks/" + id).build();
        } catch (IOException ioe){
            response = ResponseEntity.internalServerError().build();
        }
        return response;
    }

}
