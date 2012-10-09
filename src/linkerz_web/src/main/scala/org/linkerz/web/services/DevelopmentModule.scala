/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.web.services

import org.apache.tapestry5.ioc.MappedConfiguration
import org.apache.tapestry5.SymbolConstants

/**
 * The Class DevelopmentModule.
 *
 * @author Nguyen Duc Dung
 * @since 8/2/12, 10:00 PM
 *
 */

/**
 * This module is automatically included as part of the Tapestry IoC Registry if <em>tapestry.execution-mode</em>
 * includes <code>development</code>.
 */
object DevelopmentModule {

  def contributeApplicationDefaults(configuration: MappedConfiguration[String, Object]) {
    // The factory default is true but during the early stages of an application
    // overriding to false is a good idea. In addition, this is often overridden
    // on the command line as -Dtapestry.production-mode=false
    configuration.add(SymbolConstants.PRODUCTION_MODE, Boolean.box(false))

    // The application version number is incorprated into URLs for some
    // assets. Web browsers will cache assets because of the far future expires
    // header. If existing assets are changed, the version number should also
    // change, to force the browser to download new versions.
    configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT-DEV")
  }

}
