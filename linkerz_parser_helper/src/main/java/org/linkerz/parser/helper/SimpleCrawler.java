package org.linkerz.parser.helper;

import crawlercommons.fetcher.BaseFetchException;
import crawlercommons.fetcher.BaseFetcher;
import crawlercommons.fetcher.FetchedResult;
import crawlercommons.fetcher.http.SimpleHttpFetcher;
import crawlercommons.fetcher.http.UserAgent;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.linkerz.crawl.topology.factory.LinkerZUserAgent;
import org.linkerz.parser.ArticleParser;
import org.linkerz.parser.LinksParser;
import org.linkerz.parser.model.Article;
import org.linkerz.parser.model.ImageElement;
import org.linkerz.parser.model.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This simple crawler just go around the homepage.
 *
 * @author Nguyen Duc Dung
 * @since 12/29/12 1:13 AM
 */
public class SimpleCrawler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Pattern filterPattern = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|exe|msi|jar|flv|doc|docx|xls|xlsx|ppt|pptx|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    public void crawl(String url, String contentSelection, List<String> removeSelections, JLabel statusLbl, JTextArea resultTxt, Configuration configuration) throws IOException, URISyntaxException, BaseFetchException {
        List<String> testUrls = getTestUrls(url, statusLbl);
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
                        FetchedResult result = download(testUrl, statusLbl);
                        if (result != null) {
                            InputStream inputStream = new ByteArrayInputStream(result.getContent());
                            Document doc = Jsoup.parse(inputStream, null, testUrl);
                            Article article = articleParser.parse(doc, contentSelection, removeSelections);
                            if (article != null) {
                                count += 1;
                                resultTxt.append(testUrl);
                                resultTxt.append("\n");

                                if (configuration.isShowTitle()) {
                                    resultTxt.append(article.title());
                                    resultTxt.append("\n");
                                }

                                if (configuration.isShowDescription()) {
                                    resultTxt.append(article.description(30));
                                    resultTxt.append("\n");
                                }

                                if (configuration.isShowText()) {
                                    resultTxt.append(article.text());
                                    resultTxt.append("\n");
                                }

                                if (configuration.isShowImage()) {
                                    for (ImageElement element : article.imagesAsJavaList()) {
                                        resultTxt.append(element.src());
                                        resultTxt.append("\n");
                                    }
                                }

                                resultTxt.append("\n=========================================================================\n");
                                resultTxt.setCaretPosition(resultTxt.getDocument().getLength());
                            }
                            inputStream.close();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        time = System.currentTimeMillis() - time;
        statusLbl.setText("Downloaded: " + count + " websites in " + (time / 1000) + " s");
    }

    private List<String> getTestUrls(String url, JLabel statusLbl) throws IOException, BaseFetchException {
        List<String> testUrls = new ArrayList<String>();
        LinksParser linksParser = new LinksParser();
        FetchedResult result = download(url, statusLbl);
        if (result != null) {
            InputStream inputStream = new ByteArrayInputStream(result.getContent());
            Document doc = Jsoup.parse(inputStream, null, url);
            for (Link link : linksParser.parseToJavaList(doc)) {
                testUrls.add(link.url());
            }
            inputStream.close();
        }
        return testUrls;
    }

    private FetchedResult download(String url, JLabel statusLbl) throws IOException, BaseFetchException {
        BaseFetcher fetcher = new SimpleHttpFetcher(new LinkerZUserAgent());
        FetchedResult result = fetcher.get(url);
        logger.info("Download " + result.getStatusCode() + ": " + url);
        statusLbl.setText("Download " + result.getStatusCode() + ": " + url);
        if (result.getStatusCode() == HttpStatus.SC_OK && result.getContentLength() > 0) {
            return result;
        }
        return null;
    }
}
