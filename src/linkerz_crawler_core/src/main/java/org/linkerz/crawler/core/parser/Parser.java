/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.parser;

import org.linkerz.crawler.core.downloader.result.DownloadResult;
import org.linkerz.crawler.core.parser.result.ParserResult;

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
