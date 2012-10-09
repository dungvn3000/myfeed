/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.web.services.plugin

import reflect.BeanProperty
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.ParserPluginData

/**
 * The Class PluginServiceImpl.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 7:22 PM
 *
 */

class PluginServiceImpl extends PluginService {

  @BeanProperty
  var mongoOperations: MongoOperations = _

  def parserPlugins = {
    mongoOperations.findAll(classOf[ParserPluginData])
  }


  def findParserPlugin(id: String) = {
    mongoOperations.findById(id, classOf[ParserPluginData])
  }
}
