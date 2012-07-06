/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.crawler;

import org.linkerz.core.callback.CallBack;
import org.linkerz.core.callback.CallBackable;
import org.linkerz.crawler.core.controller.config.CrawlControllerConfig;
import org.linkerz.crawler.core.downloader.controller.DownloaderController;
import org.linkerz.crawler.core.downloader.result.DownloadResult;
import org.linkerz.crawler.core.job.CrawlJob;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.parser.controller.ParserController;
import org.linkerz.crawler.core.parser.result.DefaultParserResult;
import org.linkerz.crawler.core.parser.result.ParserResult;
import org.linkerz.crawler.core.session.CrawlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Pattern;


/**
 * The Class DefaultCrawler.
 * <p/>
 * Basic crawler will task a job to crawl.
 *
 * @author Nguyen Duc Dung
 * @since 7/5/12, 8:20 PM
 */
public class DefaultCrawler implements Crawler, CallBackable<ParserResult> {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g|ico" + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    private static final Logger logger = LoggerFactory.getLogger(DefaultCrawler.class);

    private final Object syncRoot = new Object();

    private DownloaderController downloaderController;
    private ParserController parserController;
    private CrawlSession session;
    private CrawlControllerConfig config;
    private CallBack<ParserResult> callBack;
    private Thread thread;

    /**
     * The flag show crawler status.
     */
    private boolean working = false;

    public DefaultCrawler(DownloaderController downloaderController, ParserController parserController,
                          CrawlSession session, CrawlControllerConfig config) {
        this.downloaderController = downloaderController;
        this.parserController = parserController;
        this.session = session;
        this.config = config;
    }

    @Override
    public boolean shouldCrawl(CrawlJob crawlJob) {
        String href = crawlJob.getWebLink().getUrl().toLowerCase();
        boolean shouldCrawl = !FILTERS.matcher(href).matches();
        if (!shouldCrawl) {
            logger.info("Skip this url " + crawlJob.getWebLink().getUrl());
        }
        return shouldCrawl;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        synchronized (syncRoot) {
            try {
                while (!session.isFinished()) {
                    working = false;
                    CrawlJob job = session.getLocalJobQueue().next();
                    if (job != null && shouldCrawl(job)) {
                        DownloadResult downloadResult;
                        working = true;
                        try {
                            downloadResult = downloaderController.get("*").download(job.getWebLink());
                            ParserResult parserResult = parserController.get("*").parse(downloadResult);
                            if (parserResult instanceof DefaultParserResult) {
                                List<WebLink> webLinks = ((DefaultParserResult) parserResult).getLinks();
                                logger.info("Crawled " + String.valueOf(webLinks.size()) + " links in "
                                        + job.getWebLink().getUrl());
                            }
                            if (callBack != null) {
                                callBack.onSuccess(parserResult);
                            }
                        } catch (Exception e) {
                            if (callBack != null) {
                                callBack.onFailed(e);
                            }
                            logger.error("Error: " + job.getWebLink().getUrl(), e);
                        }
                    }
                    // Yielding context to another thread.
                    Thread.yield();
                }

                thread.join(1000);
                logger.info("Finished...");

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void setCallBack(CallBack<ParserResult> callBack) {
        this.callBack = callBack;
    }

    @Override
    public void setThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public Thread getThread() {
        return thread;
    }

    @Override
    public boolean isWorking() {
        return working;
    }
}
