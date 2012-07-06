/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.crawler;

import org.linkerz.core.callback.CallBack;
import org.linkerz.core.callback.CallBackable;
import org.linkerz.core.queue.Queue;
import org.linkerz.crawler.core.controller.config.CrawlControllerConfig;
import org.linkerz.crawler.core.downloader.controller.DownloaderController;
import org.linkerz.crawler.core.downloader.result.DownloadResult;
import org.linkerz.crawler.core.job.CrawlJob;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.parser.controller.ParserController;
import org.linkerz.crawler.core.parser.result.DefaultParserResult;
import org.linkerz.crawler.core.parser.result.ParserResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * The Class DefaultCrawler.
 *
 * @author Nguyen Duc Dung
 * @since 7/5/12, 8:20 PM
 */
public class DefaultCrawler implements Crawler, CallBackable<ParserResult> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCrawler.class);

    private final Object syncRoot = new Object();

    private DownloaderController downloaderController;
    private ParserController parserController;
    private Queue<CrawlJob> localJobQueue;
    private CrawlControllerConfig config;
    private CallBack<ParserResult> callBack;
    private Thread thread;

    public DefaultCrawler(DownloaderController downloaderController, ParserController parserController,
                          Queue<CrawlJob> localJobQueue, CrawlControllerConfig config) {
        this.downloaderController = downloaderController;
        this.parserController = parserController;
        this.localJobQueue = localJobQueue;
        this.config = config;
    }

    @Override
    public boolean shouldCrawl(CrawlJob crawlJob) {
        return true;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        synchronized (syncRoot) {
            while (!localJobQueue.isFinished()) {
                CrawlJob job = localJobQueue.getNext();
                if (job != null && shouldCrawl(job)) {
                    DownloadResult downloadResult = null;
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
                // Yielding context to another thread
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }

            if (localJobQueue.isFinished()) {
                logger.info("Finished...");
                thread.stop();
            }
        }
    }

    @Override
    public void setCallBack(CallBack<ParserResult> callBack) {
        this.callBack = callBack;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
