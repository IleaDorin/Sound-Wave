package app;

import app.audio.Collections.PlaylistOutput;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.User;
import app.user.Artist;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Command runner.
 */
public final class CommandRunner {
    /**
     * The Object mapper.
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    private CommandRunner() {
    }

    /**
     * Search object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode search(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Filters filters = new Filters(commandInput.getFilters());
        String type = commandInput.getType();

        ArrayList<String> results = user.search(filters, type);
        String message = "Search returned " + results.size() + " results";
        //  added by me:
        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
            results.clear();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        objectNode.put("results", objectMapper.valueToTree(results));

        return objectNode;
    }

    /**
     * Select object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode select(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        String message = null;
        //  added by me:
        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            message = user.select(commandInput.getItemNumber());
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Load object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode load(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;
        //  added by me:
        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            message = user.load();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Play pause object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode playPause(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;
        //  added by me:
        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            message = user.playPause();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Repeat object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode repeat(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;
        //  added by me:
        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            message = user.repeat();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Shuffle object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode shuffle(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Integer seed = commandInput.getSeed();
        String message = null;
        //  added by me:
        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            message = user.shuffle(seed);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Forward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode forward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;
        //  added by me:
        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            message = user.forward();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Backward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode backward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;
        //  added by me:
        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            message = user.backward();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Like object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode like(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;
        //  added by me:
        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            message = user.like();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Next object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode next(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;
        //  added by me:
        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            message = user.next();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Prev object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode prev(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;
        //  added by me:
        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            message = user.prev();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Create playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode createPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;
        //  added by me:
        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            message = user.createPlaylist(commandInput.getPlaylistName(),
                    commandInput.getTimestamp());
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Add remove in playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addRemoveInPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;
        //  added by me:
        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            message = user.addRemoveInPlaylist(commandInput.getPlaylistId());
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Switch visibility object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode switchVisibility(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;
        //  added by me:
        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            message = user.switchPlaylistVisibility(commandInput.getPlaylistId());
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Show playlists object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showPlaylists(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<PlaylistOutput> playlists = user.showPlaylists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * Follow object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode follow(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;
        //  added by me:
        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            message = user.follow();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Status object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode status(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        PlayerStats stats = user.getPlayerStats();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("stats", objectMapper.valueToTree(stats));

        return objectNode;
    }

    /**
     * Show liked songs object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showLikedSongs(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<String> songs = user.showPreferredSongs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets preferred genre.
     *
     * @param commandInput the command input
     * @return the preferred genre
     */
    public static ObjectNode getPreferredGenre(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String preferredGenre = user.getPreferredGenre();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

    /**
     * Gets top 5 songs.
     *
     * @param commandInput the command input
     * @return the top 5 songs
     */
    public static ObjectNode getTop5Songs(final CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets top 5 playlists.
     *
     * @param commandInput the command input
     * @return the top 5 playlists
     */
    public static ObjectNode getTop5Playlists(final CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    //  added by me:

    /**
     * switchConnectionStatus object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode switchConnectionStatus(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (user == null) {
             message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            if (user.getUserType().equals("user")) {
                message = commandInput.getUsername() + " has changed status successfully.";
                user.setOnline();
            } else {
                message = user.getUsername() + " is not a normal user.";
            }

        }
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * getOnlineUsers object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode getOnlineUsers(final CommandInput commandInput) {
        List<String> onlineUsers = Admin.getOnlineUsersNames();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("result", objectMapper.valueToTree(onlineUsers));

        return objectNode;
    }

    /**
     * addUser object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addUser(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        int age = commandInput.getAge();
        String city = commandInput.getCity();
        String type = commandInput.getType(); // user, artist, or host
        ObjectNode output = objectMapper.createObjectNode();

        // Add user and get the result message
        String message = Admin.addUser(username, age, city, type);

        output.put("command", "addUser");
        output.put("timestamp", commandInput.getTimestamp());
        output.put("user", username);
        output.put("message", message);

        return output;
    }

    /**
     * addAlbum object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addAlbum(final CommandInput commandInput) {
        ObjectNode output = objectMapper.createObjectNode();
        String message = Admin.addAlbum(commandInput);

        output.put("command", "addAlbum");
        output.put("timestamp", commandInput.getTimestamp());
        output.put("user", commandInput.getUsername());
        output.put("message", message);
        return output;
    }

    /**
     * showAlbums object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showAlbums(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        ObjectNode output = objectMapper.createObjectNode();
        ArrayNode albumsArray = Admin.showAlbums(commandInput, objectMapper);
        output.put("command", "showAlbums");
        output.put("timestamp", commandInput.getTimestamp());
        output.put("user", username);
        output.set("result", albumsArray);

        return output;
    }

    /**
     * printCurrentPage object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode printCurrentPage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ObjectNode output = objectMapper.createObjectNode();
        String pageContent;

        if (!user.isOnline()) {
            pageContent = user.getUsername() + " is offline.";
        } else {
            pageContent = user.getCurrentPageContent();
        }

        output.put("user", commandInput.getUsername());
        output.put("command", "printCurrentPage");
        output.put("timestamp", commandInput.getTimestamp());
        output.put("message", pageContent);

        return output;
    }

    /**
     * getAllUsers object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode getAllUsers(final CommandInput commandInput) {
        ObjectNode output = objectMapper.createObjectNode();
        ArrayList<String> message = Admin.getAllUsers();

        output.put("command", "getAllUsers");
        output.put("timestamp", commandInput.getTimestamp());
        output.put("result", objectMapper.valueToTree(message));

        return output;
    }

    /**
     * addEvent object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addEvent(final CommandInput commandInput) {
        ObjectNode output = objectMapper.createObjectNode();

        User user = Admin.getUser(commandInput.getUsername());
        output.put("command", "addEvent");
        output.put("timestamp", commandInput.getTimestamp());
        output.put("user", commandInput.getUsername());
        if (user == null) {
            output.put("message", "The username " + commandInput.getUsername() + " doesn't exist.");
            return output;
        }

        if (!(user.getUserType().equals("artist"))) {
            output.put("message", commandInput.getUsername() + " is not an artist.");
            return output;
        }

        Artist artist = (Artist) user;
        String message = artist.addEvent(commandInput);
        output.put("message", message);
        return output;
    }

    /**
     * Gets top 5 albums.
     *
     * @param commandInput the command input
     * @return the top 5 albums
     */
    public static ObjectNode getTop5Albums(final CommandInput commandInput) {
        List<String> albums = Admin.getTop5Albums();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(albums));

        return objectNode;
    }

    /**
     * addPodcast object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addPodcast(final CommandInput commandInput) {
        ObjectNode output = objectMapper.createObjectNode();
        String message = Admin.addPodcast(commandInput);
        output.put("command", "addPodcast");
        output.put("timestamp", commandInput.getTimestamp());
        output.put("user", commandInput.getUsername());
        output.put("message", message);
        return output;
    }

    /**
     * removePodcast object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removePodcast(final CommandInput commandInput) {
        ObjectNode output = objectMapper.createObjectNode();

        output.put("command", "removePodcast");
        output.put("timestamp", commandInput.getTimestamp());
        output.put("user", commandInput.getUsername());

        String message = Admin.removePodcast(commandInput);

        output.put("message", message);

        return output;
    }

    /**
     * addMerch object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addMerch(final CommandInput commandInput) {
        ObjectNode output = objectMapper.createObjectNode();
        output.put("command", "addMerch");
        output.put("user", commandInput.getUsername());
        output.put("timestamp", commandInput.getTimestamp());
        String message = Admin.addMerch(commandInput);
        output.put("message", message);
        return output;
    }

    /**
     * addAnnouncement object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addAnnouncement(final CommandInput commandInput) {
        ObjectNode output = objectMapper.createObjectNode();
        output.put("command", "addAnnouncement");
        output.put("user", commandInput.getUsername());
        output.put("timestamp", commandInput.getTimestamp());
        String message = Admin.addAnnouncement(commandInput);
        output.put("message", message);
        return output;
    }

    /**
     * removeAnnouncement object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removeAnnouncement(final CommandInput commandInput) {
        ObjectNode output = objectMapper.createObjectNode();
        output.put("command", "removeAnnouncement");
        output.put("user", commandInput.getUsername());
        output.put("timestamp", commandInput.getTimestamp());
        String message = Admin.removeAnnouncement(commandInput);
        output.put("message", message);
        return output;
    }

    /**
     * showPodcasts object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showPodcasts(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        ObjectNode output = objectMapper.createObjectNode();
        ArrayNode podcastsArray = Admin.showPodcasts(commandInput, objectMapper);

        output.put("command", "showPodcasts");
        output.put("timestamp", commandInput.getTimestamp());
        output.put("user", username);
        output.set("result", podcastsArray);

        return output;
    }

    /**
     * removeAlbum object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removeAlbumt(final CommandInput commandInput) {
        ObjectNode output = objectMapper.createObjectNode();
        output.put("command", "removeAlbum");
        output.put("timestamp", commandInput.getTimestamp());
        output.put("user", commandInput.getUsername());
        String message = Admin.removeAlbum(commandInput);
        output.put("message", message);
        return output;
    }

    /**
     * removeAlbum object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode changePage(final CommandInput commandInput) {
        ObjectNode output = objectMapper.createObjectNode();
        output.put("command", "changePage");
        output.put("timestamp", commandInput.getTimestamp());
        output.put("user", commandInput.getUsername());
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (!user.isOnline()) {
            message = user.getUsername() + " is offline.";
        } else {
            message = user.changePage(commandInput.getNextPage());
        }

        output.put("message", message);
        return output;
    }
    /**
     * removeEvent object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removeEvent(final CommandInput commandInput) {
        ObjectNode output = objectMapper.createObjectNode();
        output.put("command", "removeEvent");
        output.put("user", commandInput.getUsername());
        output.put("timestamp", commandInput.getTimestamp());
        String message = Admin.removeEvent(commandInput);
        output.put("message", message);
        return output;
    }

    /**
     * deleteUser object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode deleteUser(final CommandInput commandInput) {
        ObjectNode output = objectMapper.createObjectNode();
        output.put("command", "deleteUser");
        output.put("user", commandInput.getUsername());
        output.put("timestamp", commandInput.getTimestamp());
        String message = Admin.deleteUser(commandInput);
        output.put("message", message);
        return output;
    }

    /**
     * generates top 5 artists.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode getTop5Artists(final CommandInput commandInput) {
        ObjectNode output = objectMapper.createObjectNode();
        output.put("command", "getTop5Artists");
        output.put("timestamp", commandInput.getTimestamp());
        List<String> artists = Admin.getTop5Artists();
        output.put("result:", objectMapper.valueToTree(artists));
        return output;
    }
}
