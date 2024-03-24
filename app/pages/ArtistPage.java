package app.pages;

import app.audio.Collections.Album;
import app.special.Event;
import app.special.Merch;
import app.user.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistPage extends UserPage {
    private List<Album> albums;
    private ArrayList<Merch> merches;
    private ArrayList<Event> events;

    public ArtistPage(final String artistName) {
        super(artistName, "artistPage");
        albums = new ArrayList<>();
        merches = new ArrayList<>();
        events = new ArrayList<>();
    }

    /**
     * Prints out the page's content.
     *
     * @return the page content
     */
    public final String generatePageContent() {
        StringBuilder pageContent = new StringBuilder();

        // Albums
        pageContent.append("Albums:\n\t[");
        ArrayList<String> albumNames = new ArrayList<>();
        for (Album album : albums) {
            albumNames.add(album.getName());
        }
        pageContent.append(String.join(", ", albumNames)).append("]");

        pageContent.append("\n\n");

        // Merch
        pageContent.append("Merch:\n\t[");
        if (!merches.isEmpty()) {
            for (Merch merch : merches) {
                pageContent.append(merch.getName()).append(" - ").append(merch.getPrice())
                        .append(":\n\t").append(merch.getDescription()).append(", ");
            }
            // Remove the last comma and space
            pageContent.setLength(pageContent.length() - 2);
        }
        pageContent.append("]");

        pageContent.append("\n\n");

        // Events
        pageContent.append("Events:\n\t[");
        if (!events.isEmpty()) {
            for (Event event : events) {
                pageContent.append(event.getName()).append(" - ").append(event.getDate())
                        .append(":\n\t").append(event.getDescription()).append(", ");
            }
            // Remove the last comma and space
            pageContent.setLength(pageContent.length() - 2);
        }
        pageContent.append("]");

        return pageContent.toString();
    }

    /**
     * updates the artist's page.
     *
     * @param artist the artist
     */
    public final void updateArtistPage(final Artist artist) {
        //  update the albums list
        this.albums = artist.getAlbums();
        //  update merch
        this.merches = artist.getMerches();
        //  update events
        this.events = artist.getEvents();
    }
}
