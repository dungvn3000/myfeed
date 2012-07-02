package org.linkerz.crawler.core.parser;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.model.WebPage;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class DefaultParser.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 2:15 AM
 */
public class DefaultParser extends AbstractParser implements Parser {
    @Override
    public ParserResult parse(WebPage page) {
        Document document = Jsoup.parse(page.getHtml());
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
