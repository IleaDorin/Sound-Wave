package app.special;

import lombok.Getter;

@Getter
public class Announcement {
    private String anoouncementName;
    private String description;

    public Announcement(final String anoouncementName, final String description) {
        this.anoouncementName = anoouncementName;
        this.description = description;
    }

}
