package org.linkerz.crawler.core.controller;

import org.linkerz.crawler.core.model.WebLink;

/**
 * The Class AbstractController.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:56 AM
 */
public abstract class AbstractController implements Controller {

    @Override
    public void start(WebLink webLink) {
        System.out.println(webLink.getUrl());
    }
}
