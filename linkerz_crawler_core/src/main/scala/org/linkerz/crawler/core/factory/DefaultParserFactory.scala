/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.core.factory

import org.linkerz.crawler.core.parser.DefaultParser

/**
 * The Class DefaultParserFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:46 PM
 *
 */

class DefaultParserFactory extends ParserFactory {
  def createParser() = {
    new DefaultParser
  }
}
