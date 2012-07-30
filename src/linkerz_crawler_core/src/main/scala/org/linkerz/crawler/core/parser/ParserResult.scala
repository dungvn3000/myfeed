/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.parser

import org.linkerz.crawler.core.model.{WebPage, WebUrl}

/**
 * The Class ParserResult.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:10 AM
 *
 */

case class ParserResult(webPage: WebPage, webUrls: List[WebUrl]) {

}
