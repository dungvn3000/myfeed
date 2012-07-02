package org.linkerz.crawler.core.parser;

import org.linkerz.crawler.core.downloader.DownloadResult;

/**
 * The Class Parser.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:25 AM
 */
public interface Parser<DR extends DownloadResult, PR extends ParserResult> {
    PR parse(DR downloadResult);
}
