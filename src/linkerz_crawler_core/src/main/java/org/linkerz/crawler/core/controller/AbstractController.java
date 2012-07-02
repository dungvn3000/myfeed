package org.linkerz.crawler.core.controller;

import org.linkerz.crawler.core.downloader.Downloader;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.parser.Parser;

import java.util.Map;

/**
 * The Class AbstractController.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:56 AM
 */
public abstract class AbstractController implements Controller {

    protected Map<String, Downloader> downloaders;
    protected Map<String, Parser> parsers;

    @Override
    public void start(WebLink webLink) {
        for (String urlPattern : downloaders.keySet()) {
            downloaders.get(urlPattern).download(webLink);
        }
    }

    @Override
    public void setDownloaders(Map<String, Downloader> downloaders) {
        this.downloaders = downloaders;
    }

    @Override
    public void setParsers(Map<String, Parser> parsers) {
        this.parsers = parsers;
    }
}
