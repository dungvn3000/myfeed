/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.downloader;

import edu.uci.ics.crawler4j.util.IO;
import org.linkerz.crawler.core.downloader.result.DefaultDownloadResult;
import org.linkerz.crawler.core.model.WebLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xlightweb.BodyDataSource;
import org.xlightweb.GetRequest;
import org.xlightweb.IHttpResponse;
import org.xlightweb.client.HttpClient;
import org.xsocket.connection.ConnectionUtils;

import javax.net.ssl.SSLContext;

/**
 * The Class DefaultDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 2:14 AM
 */
public class DefaultDownloader extends AbstractDownloader<DefaultDownloadResult> {

    private Logger logger = LoggerFactory.getLogger(DefaultDownloader.class);


    @Override
    public DefaultDownloadResult download(WebLink webLink) throws Exception {
        logger.debug("Download :" + webLink.getUrl());

        // pass over the SSL context (here the JSE 1.6 getDefault method will be used)
        HttpClient httpClient = new HttpClient(SSLContext.getDefault());

        // make some configurations// make some configurations
        httpClient.setMaxIdle(3);                   // configure the pooling behaviour
        httpClient.setFollowsRedirect(true);        // set follow redirects
        ConnectionUtils.registerMBean(httpClient);  // register the http client's mbean
        httpClient.setAutoHandleCookies(false);     // deactivates auto handling cookies

        // create a request
        GetRequest request = new GetRequest("http://vnexpress.net/Files/Subject/3b/bd/90/f1/hg1.jpg");

        // call it (by following redirects)
        IHttpResponse response = httpClient.call(request);

        System.out.println(response.getResponseHeader().getStatus());
        // get the redirected response
        BodyDataSource bodyDataSource = response.getBody();


        IO.writeBytesToFile(bodyDataSource.readBytes(), "/Users/dungvn3000/Desktop/test/1.jpg");

        return new DefaultDownloadResult();
    }


}
