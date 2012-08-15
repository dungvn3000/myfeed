/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.factory

import org.linkerz.crawler.core.parser.Parser

/**
 * The Class ParserFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:46 PM
 *
 */

trait ParserFactory {

  def createParser() : Parser

}
