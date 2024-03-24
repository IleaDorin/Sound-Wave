package app;

import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.EpisodeInput;
import fileio.input.CommandInput;
import fileio.input.UserInput;
import fileio.input.SongInput;
import fileio.input.PodcastInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Admin.
 */
public final class Admin {
    private static List<User> users = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static int timestamp = 0;
    private static final int LIMIT = 5;

    @Getter
    private static List<Album> albums = new ArrayList<>();

    private Admin() {
    }

    /**
     * Sets users.
     *
     * @param userInputList the user input list
     */
    public static void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    /**
     * Sets songs.
     *
     * @param songInputList the song input list
     */
    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }


    /**
     * Sets podcasts.
     *
     * @param podcastInputList the podcast input list
     */
    public static void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(),
                                         episodeInput.getDuration(),
                                         episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    /**
     * Gets songs.
     *
     * @return the songs
     */
    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    /**
     * Gets playlists.
     *
     * @return the playlists
     */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Update timestamp.
     *
     * @param newTimestamp the new timestamp
     */
    public static void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }

    /**
     * Gets top 5 songs.
     *
     * @return the top 5 songs
     */
    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= LIMIT) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Gets top 5 playlists.
     *
     * @return the top 5 playlists
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= LIMIT) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * Reset.
     */
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        timestamp = 0;
        //  added by me :
        albums = new ArrayList<>();
        podcasts = new ArrayList<>();
    }

    //  added by me:

    /**
     * Retrieves a list of usernames of all users who are currently online.
     * This method iterates through all registered users and checks their online status.
     * Only users who are marked as online will have their usernames included in the list.
     *
     * @return A list of strings representing the usernames of all online users.
     */
    public static List<String> getOnlineUsersNames() {
        List<String> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (user.isOnline() && user.getUserType().equals("user")) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }

    /**
     * Same as above but returns a list of Users not just the userNames
     *
     * @return A list of online users.
     */
    public static List<User> getOnlineUsers() {
        List<User> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (user.isOnline() && user.getUserType().equals("user")) {
                onlineUsers.add(user);
            }
        }
        return onlineUsers;
    }

    /**
     * Same as above but returns a list of all users not just online
     *
     * @return A list of users.
     */
    public static List<User> getUsers() {
        return new ArrayList<>(users);
    }

    /**
     * Adds user.
     * Checks if the username is already taken.
     *
     * @param username the username
     * @param age the age
     * @param city the city
     * @param type the user type
     * @return an appropriate message
     */
    public static String addUser(final String username, final int age, final String city,
                                 final String type) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return "The username " + username + " is already taken.";
            }
        }
        //  figure out what kind of user
        if (type.equals("artist")) {
            Artist newArtist = new Artist(username, age, city, type);
            users.add(newArtist);
        } else if (type.equals("host")) {
            Host newHost = new Host(username, age, city, type);
            users.add(newHost);
        } else {
            User newUser = new User(username, age, city, type);
            users.add(newUser);
        }
        return "The username " + username + " has been added successfully.";
    }

    /**
     * Add song to library.
     *
     * @param song the new song to be added to the library
     */
    public static void addSongToLibrary(final Song song) {
        // check if the song already exists in the library
        for (Song existingSong : songs) {
            if (existingSong.getName().equals(song.getName())
                    && existingSong.getArtist().equals(song.getArtist())) {
                // song already exists, no need to add
                return;
            }
        }
        // add new song to the library
        songs.add(song);
    }

    /**
     * Add album to library.
     *
     * @param album the new album to be added to the library
     */
    public static void addAlbumToLibrary(final Album album) {
        albums.add(album);
    }

    /**
     * Gets all the users on the platform starting with the users and then artists and hosts.
     *
     * @return the users
     */
    public static ArrayList<String> getAllUsers() {
        ArrayList<String> allUsers = new ArrayList<>();
        //  first add normal users
        for (User user : users) {
            if (user.getUserType().equals("user")) {
                allUsers.add(user.getUsername());
            }
        }
        //  then for artists
        for (User user : users) {
            if (user.getUserType().equals("artist")) {
                allUsers.add(user.getUsername());
            }
        }
        //  then for hosts
        for (User user : users) {
            if (user.getUserType().equals("host")) {
                allUsers.add(user.getUsername());
            }
        }

        return allUsers;
    }

    /**
     * Return all artists.
     *
     * @return a list of artists
     */
    public static ArrayList<Artist> getAllArtists() {
        ArrayList<Artist> artists = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals("artist")) {
                artists.add((Artist) user);
            }
        }
        return artists;
    }

    /**
     * Return all hosts.
     *
     * @return a list of hosts
     */
    public static ArrayList<Host> getAllHosts() {
        ArrayList<Host> hosts = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals("host")) {
                hosts.add((Host) user);
            }
        }
        return hosts;
    }

    /**
     * Gets top 5 albums.
     *
     * @return the top 5 albums
     */
    public static List<String> getTop5Albums() {
        albums.sort((a1, a2) -> {
            int likesCompare = Integer.compare(a2.getLikes(), a1.getLikes());
            if (likesCompare == 0) {
                return a1.getName().compareTo(a2.getName());
            }
            return likesCompare;
        });
        return albums.stream()
                .limit(LIMIT)
                .map(Album::getName)
                .collect(Collectors.toList());
    }

    /**
     * Adds an album.
     *
     * @param commandInput with the album to be added
     */
    public static String addAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;

        if (user == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        }
        if (!(user.getUserType().equals("artist"))) {
            return user.getUsername() + " is not an artist.";
        }
        //  create an album
        int releaseYear = commandInput.getReleaseYear();
        String albumName = commandInput.getName();
        String description = commandInput.getDescription();
        ArrayList<SongInput> songsInput = commandInput.getSongs();
        //  create the song list:
        ArrayList<Song> newSongs = new ArrayList<>();
        for (SongInput songInput : songsInput) {
            Song song = new Song(songInput.getName(), songInput.getDuration(),
                    songInput.getAlbum(), songInput.getTags(), songInput.getLyrics(),
                    songInput.getGenre(), songInput.getReleaseYear(),
                    songInput.getArtist());
            newSongs.add(song);
        }
        Album album = new Album(albumName, user.getUsername(),
                releaseYear, description, newSongs);
        message = user.getUsername() + ((Artist) user).addAlbum(album);

        return message;
    }

    /**
     * Adds a podcast.
     *
     * @param commandInput with podcast to be added
     */
    public static String addPodcast(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user == null) {
            return  "The username " + commandInput.getUsername() + " doesn't exist.";
        }
        if (!(user.getUserType().equals("host"))) {
            return user.getUsername() + " is not a host.";
        }
        //  create a podcast
        String podcastName = commandInput.getName();
        ArrayList<EpisodeInput> episodesInput = commandInput.getEpisodes();
        //  create the episodes list:
        ArrayList<Episode> episodes = new ArrayList<>();
        for (EpisodeInput episodeInput : episodesInput) {
            Episode episode = new Episode(episodeInput.getName(), episodeInput.getDuration(),
                    episodeInput.getDescription());
            episodes.add(episode);
        }
        Podcast podcast = new Podcast(podcastName, commandInput.getName(), episodes);

        podcasts.add(podcast);
        return user.getUsername() + ((Host) user).addPodcast(podcast);
    }

    /**
     * Removes a podcast.
     *
     * @param commandInput with the podcast to be removed
     */
    public static String removePodcast(final CommandInput commandInput) {
        String message = null;
        User user = getUser(commandInput.getUsername());
        if (user == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        }
        if (!(user.getUserType().equals("host"))) {
            return user.getUsername() + " is not a host.";
        }
        Host host = (Host) user;
        String podcastName = commandInput.getName();
        Podcast podcastToRemove = host.getPodcastByName(podcastName);
        if (podcastToRemove == null) {
            return user.getUsername() + " doesn't have a podcast with the given name.";
        }
        //  check if the podcast is listened to by a normal user
        List<User> activeUsers = Admin.getOnlineUsers();
        //  check the player loaded source for each active user
        boolean found = false;
        for (User activeUser : activeUsers) {
            if (activeUser.getPlayer().isCurrentPodcast(podcastName)) {
                found = true;
            }
        }
        if (found) {
            return user.getUsername() + " can't delete this podcast.";
        }
        //  the podcast is free to be deleted
        message = user.getUsername() + " deleted the podcast successfully.";
        //  also update the corresponding libraries

        host.removePodcast(podcastToRemove);

        podcasts.remove(podcastToRemove);
        return message;
    }

    /**
     * Adds a piece of merch.
     *
     * @param commandInput the command
     * @return the message
     */
    public static String addMerch(final CommandInput commandInput) {
        //  check for the artist existence
        User artist = getUser(commandInput.getUsername());
        if (artist == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        //  check to see if the user is an artist
        if (!artist.getUserType().equals("artist")) {
            return commandInput.getUsername() + " is not an artist.";
        }

        //  pass the rest of the checks to the Artist class:
        return ((Artist) artist).addMerch(commandInput.getName(), commandInput.getDescription(),
                commandInput.getPrice());
    }

    /**
     * Adds an announcement.
     *
     * @param commandInput the command
     * @return the message
     */
    public static String addAnnouncement(final CommandInput commandInput) {
        //  check for the host existence
        User host = getUser(commandInput.getUsername());
        if (host == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        //  check to see if the user is a host
        if (!host.getUserType().equals("host")) {
            return commandInput.getUsername() + " is not a host.";
        }

        //  pass the rest of the checks to the Host class:
        return ((Host) host).addAnnouncement(commandInput);
    }

    /**
     * Removes an announcement.
     *
     * @param commandInput the command
     * @return the message
     */
    public static String removeAnnouncement(final CommandInput commandInput) {
        //  check for the host existence
        User host = getUser(commandInput.getUsername());
        if (host == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        //  check to see if the user is a host
        if (!host.getUserType().equals("host")) {
            return commandInput.getUsername() + " is not a host.";
        }

        //  pass the rest of the checks to the Host class:
        return ((Host) host).removeAnnouncement(commandInput);
    }

    /**
     * shows podcasts.
     *
     * @param command, objectMapper the command input and the object mapper
     * @return the new node to be added to the output
     */
    public static ArrayNode showPodcasts(final CommandInput command,
                                         final ObjectMapper objectMapper) {
        ArrayNode podcastsArray = objectMapper.createArrayNode();
        User user = getUser(command.getUsername());
        Host host = (Host) user;
        for (Podcast podcast : host.getPodcasts()) {
            ObjectNode podcastNode = objectMapper.createObjectNode();
            podcastNode.put("name", podcast.getName());
            ArrayNode episodesArray = objectMapper.createArrayNode();
            for (Episode episode : podcast.getEpisodes()) {
                episodesArray.add(episode.getName());
            }
            podcastNode.set("episodes", episodesArray);
            podcastsArray.add(podcastNode);
        }
        return podcastsArray;
    }

    /**
     * shows albums.
     *
     * @param command, objectMapper the command input and the object mapper
     * @return the new node to be added to the output
     */
    public static ArrayNode showAlbums(final CommandInput command,
                                       final ObjectMapper objectMapper) {
        ArrayNode albumsArray = objectMapper.createArrayNode();
        User user = getUser(command.getUsername());
        Artist artist = (Artist) user;
        for (Album album : artist.getAlbums()) {
            ObjectNode albumNode = objectMapper.createObjectNode();
            albumNode.put("name", album.getName());
            ArrayNode songsArray = objectMapper.createArrayNode();
            for (Song song : album.getSongs()) {
                songsArray.add(song.getName());
            }
            albumNode.set("songs", songsArray);
            albumsArray.add(albumNode);
        }

        return albumsArray;
    }

    /**
     * Removes a podcast.
     *
     * @param commandInput with the podcast to be removed
     */
    public static String removeAlbum(final CommandInput commandInput) {
        String message = null;
        User user = getUser(commandInput.getUsername());
        if (user == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        }
        if (!(user.getUserType().equals("artist"))) {
            return user.getUsername() + " is not an artist.";
        }
        Artist artist = (Artist) user;
        String albumNme = commandInput.getName();
        Album albumToRemove = artist.getAlbumByName(albumNme);
        if (albumToRemove == null) {
            return user.getUsername() + " doesn't have an album with the given name.";
        }
        //  check if the album is listened to by a normal user
        List<User> activeUsers = Admin.getOnlineUsers();
        //  check the player loaded source for each active user
        boolean found = false;
        for (User activeUser : activeUsers) {
            if (activeUser.getPlayer().isCurrentAlbum(albumNme)) {
                found = true;
            }
            //  search for the album's songs in the loaded player of all users
            if (activeUser.getPlayer().getSource() != null) {
                for (Song songToFind : albumToRemove.getSongs()) {
                    if (songToFind.getName()
                            .equals(activeUser.getPlayer().getSource().getAudioFile().getName())) {
                        found = true;
                        break;
                    }
                }
            }

            // search for the album's song in all the playlists of users
            for (Song songToFind : albumToRemove.getSongs()) {
                //  for each song
                for (Playlist playlist : activeUser.getPlaylists()) {
                    if (playlist.containsSong(songToFind)) {
                        found = true;
                        break;
                    }
                }
            }

        }
        if (found) {
            return user.getUsername() + " can't delete this album.";
        }

        //  the album is free to be deleted
        message = user.getUsername() + " deleted the album successfully.";
        //  also update the corresponding libraries

        artist.removeAlbum(albumToRemove);

        albums.remove(albumToRemove);
        return message;
    }

    /**
     * Removes an event.
     *
     * @param commandInput the command
     * @return the message
     */
    public static String removeEvent(final CommandInput commandInput) {
        //  check for the artist's existence
        User artist = getUser(commandInput.getUsername());
        if (artist == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        //  check to see if the user is a host
        if (!artist.getUserType().equals("artist")) {
            return commandInput.getUsername() + " is not an artist.";
        }

        //  pass the rest of the checks to the Artist class:
        return ((Artist) artist).removeEvent(commandInput);
    }

    /**
     * deletes an user.
     *
     * @param commandInput the command
     * @return the message
     */
    public static String deleteUser(final CommandInput commandInput) {
        //  check for the user's existence
        User user = getUser(commandInput.getUsername());
        if (user == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        }
        //  check for normal user
        if (user.getUserType().equals("user")) {
            String message = DeleteUserManager.deleteNormalUser(user);
            if (message == null) {
                return user.getUsername() + " can't be deleted.";
            } else {
                users.remove(user);
            }
            return message;
        }
        //  check for host
        if (user.getUserType().equals("host")) {
            Host host = (Host) user;
            String message = DeleteUserManager.deleteHost(host);
            if (message == null) {
                return host.getUsername() + " can't be deleted.";
            } else {
                //  remove the podcasts from the Admin library
                for (Podcast podcast : host.getPodcasts()) {
                    podcasts.remove(podcast);
                }
                users.remove(host);
            }
            return message;
        }
        //  check for artist
        if (user.getUserType().equals("artist")) {
            Artist artist = (Artist) user;
            String message = DeleteUserManager.deleteArtist(artist);
            if (message == null) {
                return artist.getUsername() + " can't be deleted.";
            } else {
                //  artist can be deleted
                for (Album album : artist.getAlbums()) {
                    for (Song song : album.getSongs()) {
                        songs.remove(song);
                    }
                    albums.remove(album);
                }
                users.remove(artist);
            }
            return message;
        }
        return null;
    }

    /**
     * Gets top 5 artists.
     *
     * @return the top 5 artists
     */
    public static List<String> getTop5Artists() {
        ArrayList<Artist> artists = getAllArtists();
        artists.sort((a1, a2) -> {
            int likesComare = Integer.compare(a2.likesCount(), a1.likesCount());
            if (likesComare == 0) {
                return a1.getUsername().compareTo(a2.getUsername());
            }
            return likesComare;
        });
        return artists.stream()
                .limit(LIMIT)
                .map(Artist::getUsername)
                .collect(Collectors.toList());
    }
}
