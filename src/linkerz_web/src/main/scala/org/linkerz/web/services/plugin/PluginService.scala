/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.plugin

import org.linkerz.mongodb.model.ParserPlugin

/**
 * The Class PluginService.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 7:21 PM
 *
 */

trait PluginService {

  /**
   * Return the parser plugin list.
   * @return
   */
  def parserPlugins: java.util.List[ParserPlugin]

}
