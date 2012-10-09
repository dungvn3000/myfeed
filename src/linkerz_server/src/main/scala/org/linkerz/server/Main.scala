/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.server

import org.springframework.context.support.GenericXmlApplicationContext

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 7/30/12, 8:59 PM
 *
 */

object Main extends App {

  val context = new GenericXmlApplicationContext("application.xml")

}
