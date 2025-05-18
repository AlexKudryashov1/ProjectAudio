package com.example.service;

import com.example.model.AudioMetadata;
import com.example.parser.MP3MetadataParser;
import com.example.client.ResourceServiceClient;
import com.example.mapper.AudioFileMapper;
import com.example.model.AudioFile;
import com.example.repository.AudioFileRepository;
import com.example.response.AudioFileResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
    private final AudioFileMapper audioFileMapper; // Внедряем маппер

    public AudioFileResponse uploadAudioFile(MultipartFile file)
            throws IOException, UnsupportedAudioFileException {

        // 1. Парсим метаданные
       AudioMetadata metadata = mp3MetadataParser.parseMetadata(file.getInputStream());

        // 2. Загружаем файл в Resource Service
        UUID resourceId = UUID.fromString(resourceServiceClient.uploadResource(file));

        // 3. Сохраняем через маппер
        AudioFile savedFile = audioFileRepository.save(
                audioFileMapper.toEntity(metadata, resourceId)
        );

        return audioFileMapper.toDto(savedFile);
    }
    public AudioFileResponse getAudioFile(Long id) {
        AudioFile audioFile = audioFileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Audio file not found with id: " + id));
        return audioFileMapper.toDto(audioFile);
    }

    public void deleteAudioFile(Long id) {
        // 1. Получаем информацию о файле
        AudioFile audioFile = audioFileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Audio file not found with id: " + id));

        // 2. Удаляем из хранилища
        resourceServiceClient.deleteResource(UUID.fromString(audioFile.getResourceId()));

        // 3. Удаляем запись из БД
        audioFileRepository.deleteById(id);
    }

    public byte[] getAudioBytes(Long id) {
        AudioFile audioFile = audioFileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Audio file not found with id: " + id));

        return resourceServiceClient.getResourceBytes(String.valueOf(UUID.fromString(audioFile.getResourceId())));
    }
}
