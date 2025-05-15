package audioservice.src.main.java.com.example.audioservice.model;

import lombok.Data;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class AudioFile {
    private String title;
    private String artist;
    private String album;
    private int durationSec;
    private int bitrateKbps;
    private String resourceId;
}
}
