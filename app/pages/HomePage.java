package app.pages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends UserPage {

    private List<Song> topLikedSongs;
    private List<Playlist> topFollowedPlaylists;
    private static final int LIMIT = 5;

    public HomePage(final String username) {
        super(username, "Home");
        // Initialize the lists
        this.topLikedSongs = new ArrayList<>();
        this.topFollowedPlaylists = new ArrayList<>();
    }

    @Override
    public final String generatePageContent() {
        ArrayList<String> songNames = new ArrayList<>();
        for (Song song : topLikedSongs) {
            songNames.add(song.getName());
        }
        ArrayList<String> playlistNames = new ArrayList<>();
        for (Playlist playlist : topFollowedPlaylists) {
            playlistNames.add(playlist.getName());
        }
        return "Liked songs:\n\t" + songNames + "\n\nFollowed playlists:\n\t"
                + playlistNames;
    }

    /**
     * Updates the content of the user's page.
     *
     * @param user the user for witch to be updated the page
     */
    public final void updateContent(final User user) {
        // Update top liked songs
        topLikedSongs = user.getLikedSongs()
                .stream()
                .sorted(Comparator.comparingInt(Song::getLikes).reversed())
                .limit(LIMIT)
                .collect(Collectors.toList());

        // Update top followed playlists
        topFollowedPlaylists = user.getFollowedPlaylists()
                .stream()
                .sorted(Comparator.comparingInt(Playlist::getTotalLikes).reversed())
                .limit(LIMIT)
                .collect(Collectors.toList());
    }
}

