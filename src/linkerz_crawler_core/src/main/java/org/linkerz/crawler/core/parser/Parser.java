package org.linkerz.crawler.core.parser;

import org.linkerz.crawler.core.model.WebPage;

/**
 * The Class Parser.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:25 AM
 */
public interface Parser {
    void parse(WebPage page);
}
