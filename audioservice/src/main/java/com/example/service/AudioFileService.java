package com.example.service;

import audioservice.src.main.java.com.example.audioservice.service.MP3MetadataParser;
import com.example.client.ResourceServiceClient;
import com.example.model.AudioFile;
import com.example.repository.AudioFileRepository;
import com.example.response.AudioFileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AudioFileService {

    private final AudioFileRepository audioFileRepository;
    private final ResourceServiceClient resourceServiceClient;
    private final MP3MetadataParser mp3MetadataParser;

    public AudioFileResponse uploadAudioFile(MultipartFile file) throws IOException, UnsupportedAudioFileException {
        // 1. Parse metadata
        MP3MetadataParser.AudioMetadata metadata = mp3MetadataParser.parseMetadata(file.getInputStream());

        // 2. Upload binary to Resource Service
        UUID resourceId = UUID.fromString(resourceServiceClient.uploadResource(file));

        // 3. Save metadata
        AudioFile audioFile = AudioFile.builder()
                .title(metadata.getTitle())
                .artist(metadata.getArtist())
                .album(metadata.getAlbum())
                .durationSec(metadata.getDurationSec())
                .bitrateKbps(metadata.getBitrateKbps())
                .resourceId(String.valueOf(resourceId))
                .build();

        AudioFile savedFile = audioFileRepository.save(audioFile);
        return convertToDto(savedFile);
    }

    private AudioFileResponse convertToDto(AudioFile audioFile) {
        return AudioFileResponse.builder()
                .id(audioFile.getId())
                .title(audioFile.getTitle())
                .artist(audioFile.getArtist())
                .album(audioFile.getAlbum())
                .durationSec(audioFile.getDurationSec())
                .bitrateKbps(audioFile.getBitrateKbps())
                .resourceId(audioFile.getResourceId())
                .uploadDate(audioFile.getUploadDate())
                .build();
    }
}
