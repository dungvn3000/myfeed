package org.linkerz.crawler.core.downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.linkerz.crawler.core.model.WebLink;

import java.io.IOException;

/**
 * The Class DefaultDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 2:14 AM
 */
public class DefaultDownloader extends AbstractDownloader implements Downloader {
    @Override
    public void download(WebLink webLink) {
        Document doc = null;
        try {
            doc = Jsoup.connect(webLink.getUrl()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.select("a[href]");
        for (Element element : links) {
            System.out.println(element.attr("abs:href"));
        }
    }
}
