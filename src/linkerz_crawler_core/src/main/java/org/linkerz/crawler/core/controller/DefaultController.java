package org.linkerz.crawler.core.controller;

import org.linkerz.crawler.core.callback.JobCallBack;
import org.linkerz.crawler.core.job.CrawlJob;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.parser.DefaultParserResult;
import org.linkerz.crawler.core.parser.ParserResult;
import org.linkerz.crawler.core.queue.CrawlQueue;

/**
 * The Class DefaultController.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:57 AM
 */
public class DefaultController extends AbstractController<CrawlQueue> implements JobCallBack<ParserResult> {

    @Override
    public void start(WebLink webLink) {
        queue.setDownloaders(downloaders);
        queue.setParsers(parsers);
        queue.add(new CrawlJob(webLink, this));
        queue.run();
    }

    @Override
    public void onSuccess(ParserResult parserResult) {
        if(parserResult instanceof DefaultParserResult) {
            for (WebLink webLink : ((DefaultParserResult) parserResult).getLinks()) {
                CrawlJob job = new CrawlJob(webLink, this);
                queue.add(job);
            }
        }
    }

    @Override
    public void onFailed(Exception e, Object... prams) {
        if (prams[0] instanceof WebLink) {
            WebLink webLink = (WebLink) prams[0];
            System.err.println(webLink.getUrl());
        }
    }
}
