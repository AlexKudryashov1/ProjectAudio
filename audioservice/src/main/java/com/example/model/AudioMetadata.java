package com.example.model;

public class AudioMetadata {

    private final String title;
    private final String artist;
    private final String album;
    private final int durationSec;
    private final int bitrateKbps;

    public AudioMetadata(String title, String artist, String album,
                         int durationSec, int bitrateKbps) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.durationSec = durationSec;
        this.bitrateKbps = bitrateKbps;
    }

    // Геттеры
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public int getDurationSec() { return durationSec; }
    public int getBitrateKbps() { return bitrateKbps; }
}
