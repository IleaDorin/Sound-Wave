package app.special;

public final class Event {
    private String name;
    private String description;
    private String date; // format: dd-mm-yyyy

    public Event(final String name, final String description, final String date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

}
