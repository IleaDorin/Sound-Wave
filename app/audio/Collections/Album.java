package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.List;

@Getter
public class Album extends AudioCollection {
    private final List<Song> songs;
    private final int releaseYear;
    private final String description;

    public Album(final String name, final String owner, final int releaseDate,
                 final String description, final List<Song> songs) {
        super(name, owner);
        this.releaseYear = releaseDate;
        this.description = description;
        this.songs = songs;
    }

    @Override
    public final int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public final AudioFile getTrackByIndex(final int index) {
        if (index >= 0 && index < songs.size()) {
            return songs.get(index);
        }
        return null;
    }

    @Override
    public final boolean matchesReleaseYear(final String releaseDate) {
        return String.valueOf(this.releaseYear).equals(releaseDate);
    }

    @Override
    public final boolean isVisibleToUser(final String user) {
        return true;
    }

    /**
     * Gets the album's likes.
     *
     * @return the total amount of likes
     */
    public int getLikes() {
        int sum = 0;
        for (Song song : songs) {
            sum += song.getLikes();
        }
        return sum;
    }
}
