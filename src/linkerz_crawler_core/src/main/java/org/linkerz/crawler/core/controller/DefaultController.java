package org.linkerz.crawler.core.controller;

import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;
import org.linkerz.crawler.core.downloader.DownloadResult;
import org.linkerz.crawler.core.job.CrawlJob;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.parser.DefaultParserResult;
import org.linkerz.crawler.core.parser.ParserResult;

import java.util.List;

/**
 * The Class DefaultController.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:57 AM
 */
public class DefaultController extends AbstractController<CrawlJob> implements ItemListener<CrawlJob> {

    private boolean done = false;

    @Override
    public void start() {
        getQueue().addItemListener(this, true);
        crawl();
    }

    private void crawl() {
        while (!getQueue().isEmpty()) {
            CrawlJob job = getQueue().remove();
            try {
                DownloadResult downloadResult = downloaders.get("*").download(job.getWebLink());
                ParserResult parserResult = parsers.get("*").parse(downloadResult);
                if (parserResult instanceof DefaultParserResult) {
                    List<WebLink> webLinks = ((DefaultParserResult) parserResult).getLinks();
                    for (WebLink webLink : webLinks) {
                        getQueue().add(new CrawlJob(webLink));
                    }
                }
            } catch (Exception e) {
//                e.printStackTrace();
                System.err.println(job.getWebLink().getUrl());
            }
        }
        done = true;
    }

    @Override
    public void itemAdded(ItemEvent<CrawlJob> item) {
        if (done) {
            done = false;
            crawl();
        }
    }

    @Override
    public void itemRemoved(ItemEvent<CrawlJob> item) {
    }
}
