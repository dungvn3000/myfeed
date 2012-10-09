/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.web.pages.admin

import org.linkerz.mongodb.model.ParserPluginData
import org.apache.tapestry5.annotations.{Property, SetupRender}
import org.linkerz.web.services.plugin.PluginService
import org.apache.tapestry5.ioc.annotations.Inject

/**
 * The Class ParserPlugins.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 7:18 PM
 *
 */

class ParserPlugins {

  var plugins: java.util.List[ParserPluginData] = _

  @Property
  var plugin: ParserPluginData = _

  @Inject
  var pluginService: PluginService = _

  @SetupRender
  def initializeValue() {
    plugins = pluginService.parserPlugins
  }

}
