package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Files.Song;
import app.pages.ArtistPage;
import app.special.Event;
import app.special.Merch;
import fileio.input.CommandInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public final class Artist extends User {
    /**
     * -- GETTER --
     *  Gets all albums from the artist.
     *
     * @return a list of albums
     */
    @Getter
    private List<Album> albums;
    @Getter
    private ArrayList<Event> events;
    @Getter
    private ArrayList<Merch> merches;
    private ArtistPage artistPage;
    private static final Integer FEB_DAY = 28;
    private static final Integer MONTH_DAY = 31;
    private static final Integer MONTHS = 12;
    private static final Integer MIN_YEAR = 1900;
    private static final Integer MAX_YEAR = 2023;

    public Artist(final String username, final int age, final String city, final String type) {
        super(username, age, city, type);
        this.albums = new ArrayList<>();
        this.events = new ArrayList<>();
        this.merches = new ArrayList<>();
        artistPage = new ArtistPage(this.getUsername());
    }

    /**
     * Adds an album and also the songs to the lib.
     *
     * @param album the album to be added
     * @return the message
     */
    public String addAlbum(final Album album) {
        // Check if album with the same name exists
        for (Album existingAlbum : albums) {
            if (existingAlbum.getName().equalsIgnoreCase(album.getName())) {
                return " has another album with the same name.";
            }
        }
        // Check for duplicate songs in the new album
        Set<String> songNames = new HashSet<>();
        for (Song song : album.getSongs()) {
            if (!songNames.add(song.getName())) {
                return " has the same song at least twice in this album.";
            }
        }
        this.albums.add(album);
        //  update the songs library
        for (Song song : album.getSongs()) {
            Admin.addSongToLibrary(song);
        }
        Admin.addAlbumToLibrary(album);
        return " has added new album successfully.";
    }

    /**
     * Adds an event after a few checks.
     *
     * @param commandInput that contains all the information needed.
     * @return a message
     */
    public String addEvent(final CommandInput commandInput) {
        String date = commandInput.getDate();
        String eventName = commandInput.getName();
        String description = commandInput.getDescription();
        if (!isDateValid(date)) {
            return "Event for " + getUsername() + " does not have a valid date.";
        }

        for (Event event : events) {
            if (event.getName().equals(eventName)) {
                return getUsername() + " has another event with the same name.";
            }
        }

        Event newEvent = new Event(eventName, description, date);
        events.add(newEvent);

        return getUsername() + " has added new event successfully.";
    }

    private boolean isDateValid(final String date) {
        // Regex to check valid date format (dd-mm-yyyy)
        String regex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(19|20|21|22|23)\\d\\d$";
        if (!Pattern.matches(regex, date)) {
            return false;
        }

        // Additional checks for day and month validity
        String[] parts = date.split("-");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        if (month == 2 && day > FEB_DAY) {
            return false; // February has max 28 days
        }
        if (day > MONTH_DAY || month > MONTHS) {
            return false; // Invalid day or month
        }

        return year >= MIN_YEAR && year <= MAX_YEAR; // Year range check
    }

    /**
     * Adds a merch item after a few checks.
     *
     * @param merchName description and price of the merch to be added.
     * @return a message
     */
    public String addMerch(final String merchName, final String description,
                           final int price) {

        for (Merch merch : merches) {
            if (merch.getName().equals(merchName)) {
                return this.getUsername() + " has merchandise with the same name.";
            }
        }
        //  check price
        if (price < 0) {
            return "Price for merchandise can not be negative.";
        }

        //  all good
        Merch newMerch = new Merch(merchName, description, price);
        merches.add(newMerch);

        return this.getUsername() + " has added new merchandise successfully.";
    }

    /**
     * Finds an album by its name and returns it.
     *
     * @param albumName the name of the album to be returned
     * @return the album
     */
    public Album getAlbumByName(final String albumName) {
        for (Album album : albums) {
            if (album.getName().equals(albumName)) {
                return album;
            }
        }
        return null;
    }

    /**
     * Removes an album.
     *
     * @param album the album to be removed
     */
    public void removeAlbum(final Album album) {
        albums.remove(album);
    }

    /**
     * Removes an event after a few checks.
     *
     * @param commandInput the command with all the information
     * @return the message
     */
    public String removeEvent(final CommandInput commandInput) {
        boolean found = false;
        for (Event event : events) {
            if (event.getName().equals(commandInput.getName())) {
                found = true;
                //  delete the event
                events.remove(event);
            }
        }
        //  check if the event was found
        if (!found) {
            return this.getUsername() + " doesn't have an event with the given name.";
        }

        return this.getUsername() + " deleted the event successfully.";
    }

    /**
     * computes the total amount of likes.
     *
     * @return the likes counter
     */
    public int likesCount() {
        int sum = 0;
        for (Album album : getAlbums()) {
            for (Song song : album.getSongs()) {
                sum += song.getLikes();
            }
        }
        return sum;
    }
}

