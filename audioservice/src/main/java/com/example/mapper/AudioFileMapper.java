package com.example.mapper;
import com.example.model.AudioMetadata;
import com.example.parser.MP3MetadataParser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.model.AudioFile;
import com.example.response.AudioFileResponse;
import org.hibernate.boot.Metadata;

import java.util.UUID;


@Mapper(componentModel = "spring")
public interface AudioFileMapper {
    AudioFileResponse toDto(AudioFile audioFile);

    @Mapping(target = "resourceId", expression = "java(resourceId.toString())")
    AudioFile toEntity(AudioMetadata metadata, UUID resourceId);
}
