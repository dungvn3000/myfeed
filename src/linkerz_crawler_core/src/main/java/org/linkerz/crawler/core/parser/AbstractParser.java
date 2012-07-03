/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.parser;

import org.linkerz.crawler.core.downloader.result.DownloadResult;
import org.linkerz.crawler.core.parser.result.ParserResult;

/**
 * The Class AbstractParser.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 2:14 AM
 */
public abstract class AbstractParser<DR extends DownloadResult, PR extends ParserResult>
        implements Parser<DR, PR> {
}
