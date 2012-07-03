/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.parser;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.linkerz.crawler.core.downloader.result.DefaultDownloadResult;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.parser.result.DefaultParserResult;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class DefaultParser.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 2:15 AM
 */
public class DefaultParser extends AbstractParser<DefaultDownloadResult, DefaultParserResult> {
    @Override
    public DefaultParserResult parse(DefaultDownloadResult downloadResult) {
        Document document = Jsoup.parse(downloadResult.getWebPage().getHtml());
        Elements elements = document.select("a[href]");

        List<WebLink> webLinks = new ArrayList<WebLink>();
        for (Element element : elements) {
            String link = element.attr("abs:href");
            if (!StringUtil.isBlank(link)) {
                WebLink webLink = new WebLink(link);
                webLinks.add(webLink);
            }
        }
        return new DefaultParserResult(webLinks);
    }
}
