package org.linkerz.crawler.core.model;

/**
 * The Class WebLink.
 *
 * This class represent for the web link.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 1:02 AM
 */
public class WebLink {

    private String url;

    public WebLink(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
