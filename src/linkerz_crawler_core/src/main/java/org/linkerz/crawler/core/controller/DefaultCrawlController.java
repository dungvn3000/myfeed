/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.controller;

import org.apache.commons.collections.CollectionUtils;
import org.linkerz.core.callback.CallBack;
import org.linkerz.core.config.Configurable;
import org.linkerz.core.handler.Handler;
import org.linkerz.core.session.RunInSession;
import org.linkerz.crawler.core.controller.config.CrawlControllerConfig;
import org.linkerz.crawler.core.crawler.DefaultCrawler;
import org.linkerz.crawler.core.job.CrawlJob;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.parser.result.DefaultParserResult;
import org.linkerz.crawler.core.parser.result.ParserResult;
import org.linkerz.crawler.core.session.CrawlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class DefaultController.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:57 AM
 */
@RunInSession(sessionClass = CrawlSession.class)
public class DefaultCrawlController extends AbstractCrawlController<CrawlJob> implements Handler<CrawlJob, CrawlSession>,
        Configurable<CrawlControllerConfig>, CallBack<ParserResult> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCrawlController.class);

    private CrawlSession session;
    private CrawlControllerConfig config;

    @Override
    public boolean isFor(Class<CrawlJob> clazz) {
        return clazz == CrawlJob.class;
    }

    @Override
    public void handle(CrawlJob job, CrawlSession session) throws Exception {
        //begin a new session
        logger.info("New session with " + job.getWebLink().getUrl());
        this.session = session;
        session.getLocalJobQueue().setMaxSize(config.getMaxUrlNumberForEachJob());
        session.getLocalJobQueue().add(job);
        for (int i = 0; i < config.getNumberOfCrawler(); i++) {
            DefaultCrawler crawler = createCrawler();
            Thread thread = new Thread(crawler);
            crawler.setThread(thread);
            thread.start();
            session.getThreads().add(thread);
        }
        logger.info("End Session...");
    }

    @Override
    public void onSuccess(ParserResult result) {
        session.count();
        if (session.getCount() > config.getMaxUrlNumberForEachJob()) {
            session.getLocalJobQueue().setFinished(true);
        } else {
            DefaultParserResult parserResult = (DefaultParserResult) result;
            if (CollectionUtils.isNotEmpty(parserResult.getLinks())) {
                for (WebLink webLink : parserResult.getLinks()) {
                    session.getLocalJobQueue().add(new CrawlJob(webLink));
                }
            }
        }
    }

    @Override
    public void onFailed(Exception e) {

    }

    private DefaultCrawler createCrawler() {
        DefaultCrawler crawler = new DefaultCrawler(downloaderController, parserController,
                session.getLocalJobQueue(), config);
        crawler.setCallBack(this);
        return crawler;
    }

    @Override
    public void setConfig(CrawlControllerConfig config) {
        this.config = config;
    }
}
