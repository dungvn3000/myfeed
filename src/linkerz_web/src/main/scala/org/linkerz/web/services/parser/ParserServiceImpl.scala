/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.web.services.parser

import reflect.BeanProperty
import org.linkerz.crawler.bot.factory.ParserPluginFactory

/**
 * The Class ParserServiceImpl.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 10:12 PM
 *
 */

class ParserServiceImpl extends ParserService {

  @BeanProperty
  var parserFactory: ParserPluginFactory = _


}
