package audioservice.src.main.java.com.example.audioservice.service;

import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class MP3MetadataParser {
    public AudioMetadata parseMetadata(InputStream mp3Stream) throws UnsupportedAudioFileException, IOException {
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(mp3Stream);
        Map<String, Object> properties = fileFormat.properties();

        return new AudioMetadata(
                getProperty(properties, "title"),
                getProperty(properties, "author"),
                getProperty(properties, "album"),
                getDuration(properties),
                getBitrate(properties)
        );
    }

    private String getProperty(Map<String, Object> properties, String key) {
        Object value = properties.get(key);
        return value != null ? value.toString() : "";
    }

    private int getDuration(Map<String, Object> properties) {
        try {
            String duration = getProperty(properties, "duration");
            return duration.isEmpty() ? 0 : (int) (Long.parseLong(duration) / 1_000_000);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int getBitrate(Map<String, Object> properties) {
        try {
            String bitrate = getProperty(properties, "mp3.bitrate.nominal.bps");
            return bitrate.isEmpty() ? 0 : (int) (Float.parseFloat(bitrate) / 1000);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static class AudioMetadata {
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
}
