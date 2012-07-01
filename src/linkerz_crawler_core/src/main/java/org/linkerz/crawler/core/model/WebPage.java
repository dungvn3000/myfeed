package org.linkerz.crawler.core.model;

/**
 * The Class WebPage.
 *
 * This class represent for the web page.
 * @author Nguyen Duc Dung
 * @since 7/2/12, 1:06 AM
 */
public class WebPage {

    private WebLink webLink;
    private String title;
    private String html;

    public WebLink getWebLink() {
        return webLink;
    }

    public void setWebLink(WebLink webLink) {
        this.webLink = webLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
