package org.linkerz.crawler.core.controller;

import org.linkerz.crawler.core.downloader.Downloader;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.parser.Parser;

import java.util.List;

/**
 * The Class AbstractController.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:56 AM
 */
public abstract class AbstractController implements Controller {

    private List<Downloader> downloaders;
    private List<Parser> parsers;

    @Override
    public void start(WebLink webLink) {
        for (Downloader downloader : downloaders) {
            downloader.download(webLink);
        }
    }

    public List<Downloader> getDownloaders() {
        return downloaders;
    }

    public void setDownloaders(List<Downloader> downloaders) {
        this.downloaders = downloaders;
    }

    public List<Parser> getParsers() {
        return parsers;
    }

    public void setParsers(List<Parser> parsers) {
        this.parsers = parsers;
    }
}
