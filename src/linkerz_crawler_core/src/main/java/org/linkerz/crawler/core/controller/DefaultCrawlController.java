/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.controller;

import org.apache.commons.collections.CollectionUtils;
import org.linkerz.core.callback.CallBack;
import org.linkerz.core.config.Configurable;
import org.linkerz.core.handler.Handler;
import org.linkerz.core.queue.JobQueue;
import org.linkerz.core.queue.Queue;
import org.linkerz.core.session.Session;
import org.linkerz.crawler.core.controller.config.CrawlControllerConfig;
import org.linkerz.crawler.core.crawler.DefaultCrawler;
import org.linkerz.crawler.core.job.CrawlJob;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.parser.result.DefaultParserResult;
import org.linkerz.crawler.core.parser.result.ParserResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The Class DefaultController.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:57 AM
 */
public class DefaultCrawlController extends AbstractCrawlController<CrawlJob> implements Handler<CrawlJob>,
        Configurable<CrawlControllerConfig>, CallBack<ParserResult> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCrawlController.class);

    private CrawlControllerConfig config;
    private Queue<CrawlJob> localJobQueue;
    private List<Thread> threads;
    private int count = 0;

    @Override
    public boolean isFor(Class<CrawlJob> clazz) {
        if (localJobQueue != null && !localJobQueue.isFinished()) {
            logger.info("Reject the job because the handler is busy now " + localJobQueue.size());
            return false;
        }
        return clazz == CrawlJob.class;
    }

    @Override
    public void handle(CrawlJob job, Session session) throws Exception {
        //begin a new session
        logger.info("New session with " + job.getWebLink().getUrl());
        localJobQueue = new JobQueue<CrawlJob>(new LinkedList<CrawlJob>());
        localJobQueue.setMaxSize(config.getMaxUrlNumberForEachJob());
        localJobQueue.add(job);

        count = 0;

        threads = new ArrayList<Thread>();
        for (int i = 0; i < config.getNumberOfCrawler(); i++) {
            DefaultCrawler crawler = createCrawler();
            Thread thread = new Thread(crawler);
            crawler.setThread(thread);
            thread.start();
            threads.add(thread);
        }
    }

    @Override
    public void onSuccess(ParserResult result) {
        count += 1;
        if (count > config.getMaxUrlNumberForEachJob()) {
            localJobQueue.setFinished(true);
        } else {
            DefaultParserResult parserResult = (DefaultParserResult) result;
            if (CollectionUtils.isNotEmpty(parserResult.getLinks())) {
                for (WebLink webLink : parserResult.getLinks()) {
                    localJobQueue.add(new CrawlJob(webLink));
                }
            }
        }
    }

    @Override
    public void onFailed(Exception e) {
    }

    private DefaultCrawler createCrawler() {
        DefaultCrawler crawler = new DefaultCrawler(downloaderController, parserController,
                localJobQueue, config);
        crawler.setCallBack(this);
        return crawler;
    }

    @Override
    public void setConfig(CrawlControllerConfig config) {
        this.config = config;
    }
}
