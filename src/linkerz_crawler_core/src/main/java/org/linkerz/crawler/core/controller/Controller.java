package org.linkerz.crawler.core.controller;

import org.linkerz.crawler.core.model.WebLink;

/**
 * The Class Controller.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:22 AM
 */
public interface Controller {

    /**
     * Start crawl form this link.
     * @param webLink the link of a web site.
     */
    public void start(WebLink webLink);

}
