package app.user;

import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.special.Announcement;
import fileio.input.CommandInput;
import lombok.Getter;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Host extends User {
    private ArrayList<Podcast> podcasts;

    @Getter
    private ArrayList<Announcement> announcements;

    public Host(final String username, final int age, final String city, final String userType) {
        super(username, age, city, userType);
        this.podcasts = new ArrayList<>();
        this.announcements = new ArrayList<>();
    }

    public final ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }

    public final ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    /**
     * Adds a Podcast.
     *
     * @param podcast to be added after some checks.
     * @return a message
     */
    public final String addPodcast(final Podcast podcast) {
        // Check if podcast with the same name exists
        for (Podcast existingPodcast : podcasts) {
            if (existingPodcast.getName().equalsIgnoreCase(podcast.getName())) {
                return " has another podcast with the same name.";
            }
        }
        // Check for duplicate episodes in the new podcast
        Set<String> episodeNames = new HashSet<>();

        for (Episode episode : podcast.getEpisodes()) {
            if (!episodeNames.add(episode.getName())) {
                return " has the same episode in this podcast.";
            }
        }
        this.podcasts.add(podcast);
        return " has added new podcast successfully.";
    }
    /**
     * Removes a Podcast.
     *
     * @param podcast to be removed.
     */
    public final void removePodcast(final Podcast podcast) {
        podcasts.remove(podcast);
    }

    /**
     * Finds a Podcast by name.
     *
     * @param name of the podcast to be returned.
     * @return the podcast.
     */
    public final Podcast getPodcastByName(final String name) {
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(name)) {
                return podcast;
            }
        }
        return null;
    }
    /**
     * Adds an announcement after a few checks.
     *
     * @param commandInput contains all the information needed.
     * @return a message
     */
    public final String addAnnouncement(final CommandInput commandInput) {
        for (Announcement announcement : announcements) {
            if (announcement.getAnoouncementName().equals(commandInput.getName())) {
                return this.getUsername() + " has already added an announcement with this name.";
            }
        }
        Announcement announcement = new Announcement(commandInput.getName(),
                commandInput.getDescription());
        announcements.add(announcement);

        return this.getUsername() + " has successfully added new announcement.";
    }
    /**
     * Removes an announcement after a few checks.
     *
     * @param commandInput contains all the information needed.
     * @return a message
     */
    public final String removeAnnouncement(final CommandInput commandInput) {
        boolean found = false;  //  for finding the announcement
        for (Announcement announcement : announcements) {
            if (announcement.getAnoouncementName().equals(commandInput.getName())) {
                found = true;
                //  delete the announcement
                announcements.remove(announcement);
            }
        }
        //  check if the announcement was found int the list
        if (!found) {
            return this.getUsername() + " has no announcement with the given name.";
        }

        return this.getUsername() + " has successfully deleted the announcement.";
    }
}
