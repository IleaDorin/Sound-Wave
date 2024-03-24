package app.pages;

import lombok.Getter;

public abstract class UserPage {
    @Getter
    protected String username;
    protected String pageType;

    public final String getPageType() {
        return pageType;
    }

    public UserPage(final String username, final String pageType) {
        this.username = username;
        this.pageType = pageType;
    }

    /**
     * Generates the content of the page according to the page type.
     *
     * @return the page's content
     */
    public abstract String generatePageContent();
}

