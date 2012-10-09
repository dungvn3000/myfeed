/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.test.spring

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
