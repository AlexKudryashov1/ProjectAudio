package com.example.model;

import lombok.Data;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
public class AudioFile {
    private Long Id;
    private String title;
    private String artist;
    private String album;
    private int durationSec;
    private int bitrateKbps;
    private String resourceId;
    private LocalDateTime uploadDate;

}

