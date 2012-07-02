package org.linkerz.crawler.core.controller;

import org.linkerz.crawler.core.downloader.DefaultDownloadResult;
import org.linkerz.crawler.core.downloader.Downloader;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.model.WebPage;
import org.linkerz.crawler.core.parser.DefaultParserResult;
import org.linkerz.crawler.core.parser.Parser;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
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

    protected List<WebPage> pages = new ArrayList<WebPage>();
    protected List<WebLink> links = new ArrayList<WebLink>();

    @Override
    public void start(WebLink webLink) {
        links.add(webLink);
        process();
    }

    private void process() {
        while (links.size() > 0) {
            WebLink webLink = links.get(0);
            for (String urlPattern : downloaders.keySet()) {
                DefaultDownloadResult result = (DefaultDownloadResult)
                        downloaders.get(urlPattern).download(webLink);
                if (result.getWebPage() != null) {
                    pages.add(result.getWebPage());
                    links.remove(webLink);
                    break;
                }
            }
            for (String urlPattern : parsers.keySet()) {
                for (WebPage page : pages) {
                    DefaultParserResult result = (DefaultParserResult)
                            parsers.get(urlPattern).parse(page);
                    if (!CollectionUtils.isEmpty(result.getLinks())) {
                        links.addAll(result.getLinks());
                    }
                }
            }
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
