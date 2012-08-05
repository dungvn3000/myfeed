/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.spring

import org.springframework.context.support.GenericXmlApplicationContext

/**
 * The Class SpringContext.
 *
 * @author Nguyen Duc Dung
 * @since 8/5/12, 9:38 PM
 *
 */

trait SpringContext {

  private val _context = new GenericXmlApplicationContext("context.xml")

  def context = {
    _context
  }
}
