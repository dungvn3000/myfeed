/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.controller;

import ch.lambdaj.Lambda;
import org.linkerz.core.handler.Handler;
import org.linkerz.crawler.core.crawler.Crawler;
import org.linkerz.crawler.core.crawler.DefaultCrawler;
import org.linkerz.crawler.core.job.CrawlJob;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The Class DefaultController.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:57 AM
 */
public class DefaultController extends AbstractController<CrawlJob> implements Handler<CrawlJob> {

    private int preferLocalJobNumber;
    private int numberOfCrawler;
    private Queue<CrawlJob> localJobQueue = new LinkedList<CrawlJob>();
    private List<Thread> threads = new ArrayList<Thread>();
    private boolean started;

    public void start() {
        for (int i = 0; i < numberOfCrawler; i++) {
            Thread thread = new Thread(createCrawler());
            threads.add(thread);
        }
    }

    @Override
    public boolean isFor(Class<CrawlJob> clazz) {
        return clazz == CrawlJob.class && localJobQueue.size() < preferLocalJobNumber;
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
        return new DefaultCrawler(downloaderController, parserController, getQueue(),
                localJobQueue, preferLocalJobNumber);
    }

    public void setNumberOfCrawler(int numberOfCrawler) {
        this.numberOfCrawler = numberOfCrawler;
    }

    public void setPreferLocalJobNumber(int preferLocalJobNumber) {
        this.preferLocalJobNumber = preferLocalJobNumber;
    }
}
