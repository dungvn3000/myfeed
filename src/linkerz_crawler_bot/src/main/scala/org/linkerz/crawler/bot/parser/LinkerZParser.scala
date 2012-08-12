/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.parser

import org.linkerz.crawler.bot.plugin.{ParserPluginStatus, ParserPlugin}
import org.linkerz.mongodb.model.{ParserPluginData, Link}
import org.springframework.data.mongodb.core.MongoOperations
import reflect.BeanProperty
import collection.JavaConversions._
import collection.mutable.ListBuffer
import org.springframework.data.mongodb.core.query.{Criteria, Query}

/**
 * The Class LinkerZParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 4:30 PM
 *
 */

class LinkerZParser {

  var plugins: ListBuffer[ParserPlugin] = new ListBuffer[ParserPlugin]

  @BeanProperty
  var mongoOperations: MongoOperations = _

  def load() {

    //Clear plugin list to reload the list.
    plugins.clear()

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
  }

  /**
   * Auto find a suitable plugin for the link.
   * @param link
   * @return
   */
  def parse(link: Link): ParserPluginStatus = {
    plugins.foreach(plugin => {
      if (plugin.isMatch(link)) return plugin.parse(link)
    })
    val parserStatus = new ParserPluginStatus
    parserStatus.error("Can not find any plugin for this link")
    parserStatus.code = ParserPlugin.SKIP
    parserStatus
  }


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
    } else {
      return false
    }
    true
  }

  /**
   * Delete a plugin
   * @param pluginClass
   */
  def delete(pluginClass: String) {
    mongoOperations.remove(Query.query(Criteria.where("pluginClass").is(pluginClass)), classOf[ParserPluginData])
  }

  /**
   * Return the parser.
   * @param pluginClass
   * @return
   */
  def get(pluginClass: String): ParserPlugin = {
    val plugin = plugins.find(plugin => plugin.getClass.getName == pluginClass)
    if (!plugin.isEmpty) {
      return plugin.get
    }
    null
  }

}
