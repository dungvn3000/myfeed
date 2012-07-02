package org.linkerz.crawler.core.downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.model.WebPage;

/**
 * The Class DefaultDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 2:14 AM
 */
public class DefaultDownloader extends AbstractDownloader<DefaultDownloadResult> {
    @Override
    public DefaultDownloadResult download(WebLink webLink) throws Exception{
        System.out.println(webLink.getUrl());
        Document document = Jsoup.connect(webLink.getUrl()).get();
        if (document != null) {
            WebPage page = new WebPage();
            page.setTitle(document.title());
            page.setHtml(document.html());
            return new DefaultDownloadResult(page);
        }
        return new DefaultDownloadResult();
    }
}
