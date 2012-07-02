package org.linkerz.crawler.core.downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.model.WebPage;

import java.io.IOException;

/**
 * The Class DefaultDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 2:14 AM
 */
public class DefaultDownloader extends AbstractDownloader implements Downloader {
    @Override
    public DownloadResult download(WebLink webLink) {
        Document document = null;
        try {
            System.out.println(webLink.getUrl());
            document = Jsoup.connect(webLink.getUrl()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (document != null) {
            WebPage page = new WebPage();
            page.setTitle(document.title());
            page.setHtml(document.html());
            return new DefaultDownloadResult(page);
        }
        return new DefaultDownloadResult();
    }
}
