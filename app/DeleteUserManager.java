package app;

import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Collections.Album;
import app.audio.Files.Song;
import app.user.Artist;
import app.user.Host;
import app.user.User;

import java.util.ArrayList;

public final class DeleteUserManager {

    private DeleteUserManager() {
    }
    /**
     * deletes an user.
     *
     * @param user the user to be deleted
     * @return the message
     */
    public static String deleteNormalUser(final User user) {
        //  check to see if another user is listening to one of his playlists
        for (Playlist playlist : user.getPlaylists()) {
            for (User normalUser : Admin.getOnlineUsers()) {
                if (normalUser.getPlayer().isCurrentPlaylist(playlist.getName())) {
                    return null;
                }
            }
        }

        //  remove his playlists from other people followed playlists
        ArrayList<Playlist> userPlaylists = user.getPlaylists();
        for (Playlist userPlaylist : userPlaylists) {
            for (User normalUser : Admin.getUsers()) {
                ArrayList<Playlist> followedPlaylists = normalUser.getFollowedPlaylists();
                if (followedPlaylists.contains(userPlaylist)) {
                    normalUser.removePlaylistFromFollow(userPlaylist);
                }
            }
        }
        //  remove all of his likes/follows
        for (Playlist followedPlaylist : user.getFollowedPlaylists()) {
            for (Playlist existingPlaylist : Admin.getPlaylists()) {
                if (existingPlaylist.getName().equals(followedPlaylist.getName())) {
                    //  found a followed playlist
                    existingPlaylist.decreaseFollowers();
                }
            }
        }
        for (Song likedSongs : user.getLikedSongs()) {
            for (Song allSongs : Admin.getSongs()) {
                if (likedSongs.getName().equals(allSongs.getName())) {
                    allSongs.dislike();
                }
            }
        }
        for (Artist artist : Admin.getAllArtists()) {
            for (Album album : artist.getAlbums()) {
                for (Song song : album.getSongs()) {
                    song.dislike();
                }
            }
        }

        return user.getUsername() + " was successfully deleted.";
    }

    /**
     * deletes a host.
     * if the method returns null it means the host cannot be deleted
     *
     * @param host the host to be deleted
     * @return the message
     */
    public static String deleteHost(final Host host) {
        for (Podcast podcast : host.getPodcasts()) {
            //  check for every podcast if it s in the player of a user
            for (User normalUser : Admin.getOnlineUsers()) {
                if (normalUser.getPlayer().isCurrentPodcast(podcast.getName())) {
                    return null;
                }
                //  check for all the user's pages
                if (normalUser.getCurrentPage().getPageType().equals("hostPage")) {
                    if (normalUser.getCurrentPage().getUsername().equals(host.getUsername())) {
                        return null;
                    }
                }
            }
        }
        for (Song likedSongs : host.getLikedSongs()) {
            for (Song allSongs : Admin.getSongs()) {
                if (likedSongs.getName().equals(allSongs.getName())) {
                    allSongs.dislike();
                }
            }
        }
        for (Artist artist : Admin.getAllArtists()) {
            for (Album album : artist.getAlbums()) {
                for (Song song : album.getSongs()) {
                    song.dislike();
                }
            }
        }
        return host.getUsername() + " was successfully deleted.";
    }

    /**
     * deletes an artist.
     * if the method returns null it means the artist cannot be deleted
     *
     * @param artist the artist to be deleted
     * @return the message
     */
    public static String deleteArtist(final Artist artist) {

        for (Album album : artist.getAlbums()) {
            //  check for every album if it s in the player of a user
            for (User normalUser : Admin.getOnlineUsers()) {
                if (normalUser.getPlayer().isCurrentAlbum(album.getName())) {
                    return null;
                }
                //  search for the album's songs in the loaded player of all users
                if (normalUser.getPlayer().getSource() != null) {
                    for (Song songToFind : album.getSongs()) {
                        if (songToFind.getName().equals(normalUser
                                .getPlayer().getSource().getAudioFile().getName())) {
                            return null;
                        }
                    }
                }

                //  check for all the user's pages
                if (normalUser.getCurrentPage().getPageType().equals("artistPage")) {
                    if (normalUser.getCurrentPage().getUsername().equals(artist.getUsername())) {
                        return null;
                    }
                }
                //  remove song from liked songs
                for (Song song : album.getSongs()) {
                    normalUser.removeSongFromLiked(song);
                }
            }
            //  check if any of the songs are in smbd playlists
        }
        for (Song likedSongs : artist.getLikedSongs()) {
            for (Song allSongs : Admin.getSongs()) {
                if (likedSongs.getName().equals(allSongs.getName())) {
                    allSongs.dislike();
                }
            }
        }
        for (Artist artists : Admin.getAllArtists()) {
            for (Album album : artists.getAlbums()) {
                for (Song song : album.getSongs()) {
                    song.dislike();
                }
            }
        }
        return artist.getUsername() + " was successfully deleted.";
    }
}
