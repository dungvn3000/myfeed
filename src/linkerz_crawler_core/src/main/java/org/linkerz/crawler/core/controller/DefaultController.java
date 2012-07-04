/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.controller;

import org.linkerz.core.handler.Handler;
import org.linkerz.crawler.core.downloader.result.DownloadResult;
import org.linkerz.crawler.core.job.CrawlJob;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.parser.result.DefaultParserResult;
import org.linkerz.crawler.core.parser.result.ParserResult;

import java.util.List;

/**
 * The Class DefaultController.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:57 AM
 */
public class DefaultController extends AbstractController<CrawlJob> implements Handler<CrawlJob> {

    @Override
    public boolean isFor(Class<CrawlJob> clazz) {
        return clazz == CrawlJob.class;
    }

    @Override
    public void handle(CrawlJob job) throws Exception {
        DownloadResult downloadResult = downloaders.get("*").download(job.getWebLink());
        ParserResult parserResult = parsers.get("*").parse(downloadResult);
        if (parserResult instanceof DefaultParserResult) {
            List<WebLink> webLinks = ((DefaultParserResult) parserResult).getLinks();
            for (WebLink webLink : webLinks) {
                getQueue().add(new CrawlJob(webLink));
            }
        }
    }

}
