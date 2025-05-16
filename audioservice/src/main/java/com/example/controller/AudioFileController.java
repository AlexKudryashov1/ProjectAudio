package com.example.controller;

import com.example.response.AudioFileResponse;
import com.example.service.AudioFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

@RestController
@RequestMapping("/api/audio")
@RequiredArgsConstructor
public class AudioFileController {

    private final AudioFileService audioFileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка аудиофайла")
    public ResponseEntity<AudioFileResponse> uploadAudioFile(
            @RequestParam("file")  MultipartFile file) throws UnsupportedAudioFileException, IOException {
        AudioFileResponse response = audioFileService.uploadAudioFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации об аудиофайле")
    public ResponseEntity<AudioFileResponse> getAudioFile(
            @PathVariable Long id) {
        AudioFileResponse response = audioFileService.getAudioFile(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление аудиофайла")
    public ResponseEntity<Void> deleteAudioFile(
            @PathVariable Long id) {
        audioFileService.deleteAudioFile(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/stream")
    @Operation(summary = "Потоковое воспроизведение аудио")
    public ResponseEntity<byte[]> streamAudioFile(
            @PathVariable Long id,
            @RequestHeader(value = "Range", required = false) String rangeHeader) {
        // Реализация потоковой передачи будет через Resource Service
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(audioFileService.getAudioBytes(id));
    }
}
