/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.downloader;

import org.apache.http.HttpStatus;
import org.linkerz.crawler.core.downloader.result.DefaultDownloadResult;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.model.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xlightweb.BodyDataSource;
import org.xlightweb.GetRequest;
import org.xlightweb.IHttpResponse;
import org.xlightweb.client.HttpClient;
import org.xsocket.connection.ConnectionUtils;

import static org.xlightweb.client.HttpClient.FollowsRedirectMode;

/**
 * The Class DefaultDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 2:14 AM
 */
public class DefaultDownloader extends AbstractDownloader<DefaultDownloadResult> {

    private final static Logger logger = LoggerFactory.getLogger(DefaultDownloader.class);

    @Override
    public DefaultDownloadResult download(WebLink webLink) throws Exception {
        logger.info("Download :" + webLink.getUrl());
        HttpClient httpClient = new HttpClient();
        // make some configurations// make some configurations
        httpClient.setMaxIdle(3);                   // configure the pooling behaviour
        httpClient.setFollowsRedirectMode(FollowsRedirectMode.ALL);        // set follow redirects
        ConnectionUtils.registerMBean(httpClient);  // register the http client's mbean

        // create a request
        GetRequest request = new GetRequest(webLink.getUrl());
        request.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:10.0.2) Gecko/20100101 Firefox/10.0.2");

        // call it (by following redirects)
        IHttpResponse response = httpClient.call(request);

        DefaultDownloadResult result = new DefaultDownloadResult();
        result.setHttpStatus(response.getStatus());
        if (response.getStatus() == HttpStatus.SC_OK) {
            // get the redirected response
            BodyDataSource bodyDataSource = response.getBody();
            WebPage webPage = new WebPage();
            webPage.setWebLink(webLink);
            webPage.setHtml(bodyDataSource.readString());
            result.setWebPage(webPage);
        }
        return result;
    }


}
