package app.pages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;

import java.util.ArrayList;

public class LikedContentPage extends UserPage {
    private ArrayList<Song> likedSongs;
    private ArrayList<Playlist> followedPlaylists;

    public LikedContentPage(final String username) {
        super(username, "LikedContent");
        this.likedSongs = new ArrayList<>();
        this.followedPlaylists = new ArrayList<>();
    }

    @Override
    public final String generatePageContent() {
        StringBuilder pageContent = new StringBuilder();

        // Liked Songs
        pageContent.append("Liked songs:\n\t[");
        if (!likedSongs.isEmpty()) {
            for (Song song : likedSongs) {
                pageContent.append(song.getName()).append(" - ")
                        .append(song.getArtist())
                        .append(", ");
            }
            // Remove the last comma and space
            pageContent.setLength(pageContent.length() - 2);
        }
        pageContent.append("]");

        pageContent.append("\n\n");

        // Followed Playlists
        pageContent.append("Followed playlists:\n\t[");
        if (!followedPlaylists.isEmpty()) {
            for (Playlist playlist : followedPlaylists) {
                pageContent.append(playlist.getName()).append(" - ")
                        .append(playlist.getOwner())
                        .append(", ");
            }
            // Remove the last comma and space
            pageContent.setLength(pageContent.length() - 2);
        }
        pageContent.append("]");

        return pageContent.toString();
    }

    /**
     * Updates the content of the user's liked content page.
     *
     * @param user the user for witch to be updated the page
     */
    public final void updateLikedContent(final User user) {
        // Update top liked songs
        likedSongs = user.getLikedSongs();

        // Update top followed playlists
        followedPlaylists = user.getFollowedPlaylists();
    }
}
