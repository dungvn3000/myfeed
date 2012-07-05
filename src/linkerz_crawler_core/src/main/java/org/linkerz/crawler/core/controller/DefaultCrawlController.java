/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.controller;

import ch.lambdaj.Lambda;
import org.linkerz.core.config.Configurable;
import org.linkerz.core.handler.Handler;
import org.linkerz.core.queue.JobQueue;
import org.linkerz.core.queue.Queue;
import org.linkerz.crawler.core.controller.config.CrawlControllerConfig;
import org.linkerz.crawler.core.crawler.Crawler;
import org.linkerz.crawler.core.crawler.DefaultCrawler;
import org.linkerz.crawler.core.job.CrawlJob;
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
        Configurable<CrawlControllerConfig> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCrawlController.class);

    private CrawlControllerConfig config;
    private Queue<CrawlJob> localJobQueue;
    private List<Thread> threads = new ArrayList<Thread>();
    private boolean started = false;

    public void start() {
        localJobQueue = new JobQueue<CrawlJob>(new LinkedList<CrawlJob>());
        localJobQueue.setMaxSize(config.getMaxJobNumber());
        for (int i = 0; i < config.getNumberOfCrawler(); i++) {
            Thread thread = new Thread(createCrawler());
            threads.add(thread);
        }
    }

    @Override
    public boolean isFor(Class<CrawlJob> clazz) {
        if (localJobQueue.size() > config.getPreferLocalJobNumber()) {
            logger.info("Reject the job because the handler is too busy now " + localJobQueue.size());
        }
        return clazz == CrawlJob.class && localJobQueue.size() < config.getPreferLocalJobNumber();
    }

    @Override
    public void handle(CrawlJob job) throws Exception {
        if (!started) {
            Lambda.forEach(threads).start();
            started = true;
        }
        localJobQueue.add(job);
    }

    private Crawler createCrawler() {
        DefaultCrawler crawler = new DefaultCrawler(downloaderController, parserController, getQueue(),
                localJobQueue, config);
        return crawler;
    }

    @Override
    public void setConfig(CrawlControllerConfig config) {
        this.config = config;
    }
}
