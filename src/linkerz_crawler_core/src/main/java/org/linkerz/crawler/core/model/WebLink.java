/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.model;

import java.io.Serializable;

/**
 * The Class WebLink.
 *
 * This class represent for the web link.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 1:02 AM
 */
public class WebLink implements Serializable {

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
