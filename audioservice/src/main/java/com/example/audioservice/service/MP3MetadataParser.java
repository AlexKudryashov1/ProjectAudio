package audioservice.src.main.java.com.example.audioservice.service;

import audioservice.src.main.java.com.example.audioservice.model.AudioFile;



import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.ID3v2Tag;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class MP3MetadataParser {
    public AudioFile parse(MultipartFile file) throws IOException, Exception {
        MP3File mp3 = new MP3File(file.getInputStream());
        ID3v2Tag tag = mp3.getID3v2Tag();

        return AudioFile.builder()
                .title(tag.getTitle() != null ? tag.getTitle() : "Unknown")
                .artist(tag.getArtist() != null ? tag.getArtist() : "Unknown")
                .album(tag.getAlbum() != null ? tag.getAlbum() : "Unknown")
                .durationSec(mp3.getMP3AudioHeader().getTrackLength())
                .bitrateKbps(mp3.getMP3AudioHeader().getBitRateAsNumber())
                .build();
    }
}
