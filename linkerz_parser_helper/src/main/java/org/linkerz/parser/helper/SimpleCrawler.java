package org.linkerz.parser.helper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.utils.URIUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.linkerz.crawl.topology.downloader.Downloader;
import org.linkerz.crawl.topology.factory.DownloadFactory;
import org.linkerz.parser.ArticleParser;
import org.linkerz.parser.LinksParser;
import org.linkerz.parser.model.Article;
import org.linkerz.parser.model.ImageElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * This simple crawler just go around the homepage.
 *
 * @author Nguyen Duc Dung
 * @since 12/29/12 1:13 AM
 */
public class SimpleCrawler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Downloader downloader = DownloadFactory.createDownloader();

    private Pattern filterPattern = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|exe|msi|jar|flv|doc|docx|xls|xlsx|ppt|pptx|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    public void crawl(String url, String contentSelection, List<String> removeSelections, JLabel statusLbl, JTextArea resultTxt, Configuration configuration) throws IOException, URISyntaxException {
        Set<String> testUrls = getTestUrls(url, statusLbl);
        ArticleParser articleParser = new ArticleParser();
        resultTxt.setText("");
        HttpHost httpHost = URIUtils.extractHost(new URI(url));
        String domainName = httpHost.getHostName();

        int count = 0;
        long time = System.currentTimeMillis();
        for (String testUrl : testUrls) {
            if (!filterPattern.matcher(testUrl).matches()) {
                HttpHost testHttpHost = URIUtils.extractHost(new URI(testUrl));
                String testDomainName = testHttpHost.getHostName();
                if (domainName.equals(testDomainName)) {
                    try {
                        HttpEntity testEntity = download(testUrl, statusLbl);
                        if (testEntity != null) {
                            Document doc = Jsoup.parse(testEntity.getContent(), "UTF-8", testUrl);
                            Article article = articleParser.parse(doc, contentSelection, removeSelections);
                            if (article != null) {
                                count += 1;
                                resultTxt.append(testUrl);
                                resultTxt.append("\n");

                                if(configuration.isShowTitle()) {
                                    resultTxt.append(article.title());
                                    resultTxt.append("\n");
                                }

                                if(configuration.isShowDescription()) {
                                    resultTxt.append(article.description(30));
                                    resultTxt.append("\n");
                                }

                                if(configuration.isShowText()) {
                                    resultTxt.append(article.text());
                                    resultTxt.append("\n");
                                }

                                if(configuration.isShowImage()) {
                                    for (ImageElement element : article.imagesAsJavaList()) {
                                        resultTxt.append(element.src());
                                        resultTxt.append("\n");
                                    }
                                }

                                resultTxt.append("\n=========================================================================\n");
                                resultTxt.setCaretPosition(resultTxt.getDocument().getLength());
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        time = System.currentTimeMillis() - time;
        statusLbl.setText("Downloaded: " + count + " websites in " + time + " ms");
    }

    private Set<String> getTestUrls(String url, JLabel statusLbl) throws IOException {
        Set<String> testUrls = Collections.emptySet();
        LinksParser linksParser = new LinksParser();
        HttpEntity entity = download(url, statusLbl);
        if (entity != null) {
            Document doc = Jsoup.parse(entity.getContent(), "UTF-8", url);
            testUrls = linksParser.parse(doc);
        }
        return testUrls;
    }

    private HttpEntity download(String url, JLabel statusLbl) throws IOException {
        HttpResponse response = downloader.download(url);
        logger.info("Download " + response.getStatusLine().getStatusCode() + ": " + url);
        statusLbl.setText("Download " + response.getStatusLine().getStatusCode() + ": " + url);
        HttpEntity entity = response.getEntity();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            if (entity.getContentEncoding() != null && entity.getContentEncoding().getValue().contains("gzip")) {
                entity = new GzipDecompressingEntity(entity);
            }
        } else {
            entity = null;
        }
        return entity;
    }
}
