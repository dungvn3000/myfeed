/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.factory

import reflect.BeanProperty
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.model.model.ParserPluginData
import org.linkerz.crawler.bot.plugin.ParserPlugin
import org.springframework.data.mongodb.core.query.{Criteria, Query}
import collection.mutable.ListBuffer
import collection.JavaConversions._
import org.linkerz.crawler.bot.parser.LinkerZParser
import org.linkerz.crawler.core.parser.Parser
import org.linkerz.crawler.core.factory.ParserFactory

/**
 * The Class ParserFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 4:39 AM
 *
 */

class ParserPluginFactory extends ParserFactory {

  @BeanProperty
  var mongoOperations: MongoOperations = _

  /**
   * Install a plugin
   * @param pluginClass
   * @return
   */
  def install(pluginClass: String): Boolean = {
    //Check on the database whether the plugin was installed.
    val result = mongoOperations.findOne(Query.query(Criteria.where("pluginClass").is(pluginClass)), classOf[ParserPluginData])

    if (result == null) {
      val plugin = Class.forName(pluginClass).newInstance.asInstanceOf[ParserPlugin]
      mongoOperations.save(plugin.pluginData)
      return true
    }
    false
  }

  /**
   * Delete a plugin
   * @param pluginClass
   */
  def delete(pluginClass: String) {
    mongoOperations.remove(Query.query(Criteria.where("pluginClass").is(pluginClass)), classOf[ParserPluginData])
  }

  override def createParser: Parser = {
    var plugins = new ListBuffer[ParserPlugin]

    //Step 1: Load plugin list inside the database
    val pluginList = mongoOperations.findAll(classOf[ParserPluginData])

    //Step 2: Load data for the plugin, if custom data using default data inside each plugin.
    pluginList.foreach(pluginData => {
      if (pluginData.enable) {
        val plugin = Class.forName(pluginData.pluginClass).newInstance.asInstanceOf[ParserPlugin]
        plugin.pluginData = pluginData
        plugins += plugin
      }
    })

    new LinkerZParser(plugins.toList)
  }

}
