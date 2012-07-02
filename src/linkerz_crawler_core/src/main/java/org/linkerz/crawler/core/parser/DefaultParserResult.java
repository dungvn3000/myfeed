package org.linkerz.crawler.core.parser;

import org.linkerz.crawler.core.model.WebLink;

import java.util.List;

/**
 * The Class DefaultParserResult.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 6:26 PM
 */
public class DefaultParserResult implements ParserResult {
    private List<WebLink> links;

    public DefaultParserResult() {
    }

    public DefaultParserResult(List<WebLink> links) {
        this.links = links;
    }

    public List<WebLink> getLinks() {
        return links;
    }

    public void setLinks(List<WebLink> links) {
        this.links = links;
    }
}
