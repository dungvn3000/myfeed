package org.linkerz.crawler.core.controller;

import org.linkerz.crawler.core.downloader.Downloader;
import org.linkerz.crawler.core.parser.Parser;
import org.linkerz.crawler.core.queue.Queue;

import java.util.Map;

/**
 * The Class AbstractController.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:56 AM
 */
public abstract class AbstractController<Q extends Queue> implements Controller<Q> {

    protected Map<String, Downloader> downloaders;
    protected Map<String, Parser> parsers;
    protected Q queue;

    @Override
    public void setQueue(Q queue) {
        this.queue = queue;
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
