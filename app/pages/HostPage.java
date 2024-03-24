package app.pages;

import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.special.Announcement;
import app.user.Host;

import java.util.ArrayList;
import java.util.List;

public class HostPage extends UserPage {

    private List<Podcast> podcasts;
    private ArrayList<Announcement> announcements;

    public HostPage(final String username) {
        super(username, "hostPage");
        podcasts = new ArrayList<>();
        announcements = new ArrayList<>();
    }

    @Override
    public final String generatePageContent() {
        StringBuilder pageContent = new StringBuilder();

        // Podcasts
        pageContent.append("Podcasts:\n\t[");
        if (!podcasts.isEmpty()) {
            for (Podcast podcast : podcasts) {
                pageContent.append(podcast.getName()).append(":\n\t[");
                List<Episode> episodes = podcast.getEpisodes();
                for (Episode episode : episodes) {
                    pageContent.append(episode.getName()).append(" - ")
                            .append(episode.getDescription())
                            .append(", ");
                }

                // Remove the last comma and space
                if (!episodes.isEmpty()) {
                    pageContent.setLength(pageContent.length() - 2);
                }
                pageContent.append("]");
                pageContent.append("\n");
                pageContent.append(", ");
            }
            // Remove the last comma and space
            pageContent.setLength(pageContent.length() - 2);
        }
        pageContent.append("]");

        pageContent.append("\n\n");

        // Announcements
        pageContent.append("Announcements:\n\t");
        if (!announcements.isEmpty()) {
            pageContent.append("[");
            for (Announcement announcement : announcements) {
                pageContent.append(announcement.getAnoouncementName())
                        .append(":")
                        .append("\n\t").append(announcement.getDescription())
                        .append("\n, ");
            }
            // Remove the last comma and newline
            pageContent.setLength(pageContent.length() - 2);
            pageContent.append("]");
        } else {
            pageContent.append("[]");
        }

        return pageContent.toString();
    }

    /**
     * Updates the content of the host's page.
     *
     * @param host the user for witch to be updated the page
     */
    public final void updateHostPage(final Host host) {
        //  update the podcast list
        this.podcasts = host.getPodcasts();
        //  update the announcements list
        this.announcements = host.getAnnouncements();
    }
}
