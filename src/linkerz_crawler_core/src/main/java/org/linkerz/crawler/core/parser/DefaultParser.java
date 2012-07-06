/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.parser;

import edu.uci.ics.crawler4j.parser.ExtractedUrlAnchorPair;
import edu.uci.ics.crawler4j.parser.HtmlContentHandler;
import edu.uci.ics.crawler4j.url.URLCanonicalizer;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.linkerz.crawler.core.downloader.result.DefaultDownloadResult;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.parser.result.DefaultParserResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class DefaultParser.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 2:15 AM
 */
public class DefaultParser extends AbstractParser<DefaultDownloadResult, DefaultParserResult> {

    private final static Logger logger = LoggerFactory.getLogger(DefaultParser.class);

    private HtmlParser htmlParser = new HtmlParser();
    private ParseContext parseContext = new ParseContext();

    @Override
    public DefaultParserResult parse(DefaultDownloadResult downloadResult) {
        List<WebLink> webLinks = new ArrayList<WebLink>();
        if (downloadResult.getWebPage() != null) {
            Metadata metadata = new Metadata();
            HtmlContentHandler contentHandler = new HtmlContentHandler();

            InputStream inputStream = null;
            try {
                inputStream = new ByteArrayInputStream(downloadResult.getWebPage().getHtml().getBytes());
                htmlParser.parse(inputStream, contentHandler, metadata, parseContext);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            downloadResult.getWebPage().setTitle(metadata.get(Metadata.TITLE));

            String baseURL = contentHandler.getBaseUrl();
            String contextURL = downloadResult.getWebPage().getWebLink().getUrl();
            if (baseURL != null) {
                contextURL = baseURL;
            }

            for (ExtractedUrlAnchorPair urlAnchorPair : contentHandler.getOutgoingUrls()) {
                String href = urlAnchorPair.getHref();
                href = href.trim();
                if (href.length() == 0) {
                    continue;
                }
                String hrefWithoutProtocol = href.toLowerCase();
                if (href.startsWith("http://")) {
                    hrefWithoutProtocol = href.substring(7);
                }
                if (!hrefWithoutProtocol.contains("javascript:") && !hrefWithoutProtocol.contains("@")) {
                    String url = URLCanonicalizer.getCanonicalURL(href, contextURL);
                    if (url != null) {
                        webLinks.add(new WebLink(url));
                    }
                }
            }
        }

        return new DefaultParserResult(webLinks);
    }
}
