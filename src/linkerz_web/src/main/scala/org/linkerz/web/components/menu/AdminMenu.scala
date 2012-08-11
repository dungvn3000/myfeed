/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.components.menu

import org.apache.tapestry5.annotations.Property
import org.apache.tapestry5.ComponentResources
import org.apache.tapestry5.ioc.annotations.Inject

/**
 * The Class AdminMenu.
 *
 * @author Nguyen Duc Dung
 * @since 8/10/12, 9:25 PM
 *
 */

class AdminMenu {

  @Property
  var page: Page = _

  @Inject
  var resources: ComponentResources = _

  def getActiveClass: String = {
    if (resources.getPageName.equalsIgnoreCase(page.path)) {
      return "active"
    }
    null
  }

  def getPages = {
    Array(
      new Page("admin/ParserPlugins", "Parser Plugins"),
      new Page("admin/ParserTool", " Parser Tool")
    )
  }

  case class Page(path: String, name: String)

}
