package org.linkerz.crawler.core.parser;

import org.linkerz.crawler.core.downloader.DownloadResult;

import java.io.Serializable;

/**
 * The Class Parser.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:25 AM
 */
public interface Parser<DR extends DownloadResult, PR extends ParserResult> extends Serializable {
    PR parse(DR downloadResult);
}
