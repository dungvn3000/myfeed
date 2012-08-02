/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services

import org.apache.tapestry5.ioc.{MappedConfiguration, ServiceBinder}
import org.apache.tapestry5.SymbolConstants
import org.slf4j.Logger
import org.apache.tapestry5.services.{RequestHandler, Response, Request, RequestFilter}
import org.got5.tapestry5.jquery.JQuerySymbolConstants


/**
 * The Class AppModule.
 *
 * @author Nguyen Duc Dung
 * @since 8/2/12, 9:52 PM
 *
 */

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
object AppModule {

  def bind(binder: ServiceBinder) {
    // binder.bind(MyServiceInterface.class, MyServiceImpl.class);

    // Make bind() calls on the binder object to define most IoC services.
    // Use service builder methods (example below) when the implementation
    // is provided inline, or requires more initialization than simply
    // invoking the constructor.
  }

  def contributeFactoryDefaults(configuration: MappedConfiguration[String, Object]) {
    // The application version number is incorprated into URLs for some
    // assets. Web browsers will cache assets because of the far future expires
    // header. If existing assets are changed, the version number should also
    // change, to force the browser to download new versions. This overrides Tapesty's default
    // (a random hexadecimal number), but may be further overriden by DevelopmentModule or
    // QaModule.
    configuration.`override`(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT")
  }

  def contributeApplicationDefaults(configuration: MappedConfiguration[String, Object]) {
    // Contributions to ApplicationDefaults will override any contributions to
    // FactoryDefaults (with the same key). Here we're restricting the supported
    // locales to just "en" (English). As you add localised message catalogs and other assets,
    // you can extend this list of locales (it's a comma separated series of locale names;
    // the first locale name is the default when there's no reasonable match).
    configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en")

    //Disable PrototypeJS using JQuery instead
    configuration.add(JQuerySymbolConstants.SUPPRESS_PROTOTYPE, "true")
  }

  /**
   * This is a service definition, the service will be named "TimingFilter". The interface,
   * RequestFilter, is used within the RequestHandler service pipeline, which is built from the
   * RequestHandler service configuration. Tapestry IoC is responsible for passing in an
   * appropriate Logger instance. Requests for static resources are handled at a higher level, so
   * this filter will only be invoked for Tapestry related requests.
   * <p/>
   * <p/>
   * Service builder methods are useful when the implementation is inline as an inner class
   * (as here) or require some other kind of special initialization. In most cases,
   * use the static bind() method instead.
   * <p/>
   * <p/>
   * If this method was named "build", then the service id would be taken from the
   * service interface and would be "RequestFilter".  Since Tapestry already defines
   * a service named "RequestFilter" we use an explicit service id that we can reference
   * inside the contribution method.
   */
  def buildTimingFilter(log: Logger): RequestFilter = {
    new RequestFilter() {
      def service(request: Request, response: Response, handler: RequestHandler) = {
        val startTime = System.currentTimeMillis()
        try {
          // The responsibility of a filter is to invoke the corresponding method
          // in the handler. When you chain multiple filters together, each filter
          // received a handler that is a bridge to the next filter.
          handler.service(request, response)
        } finally {
          val elapsed = System.currentTimeMillis() - startTime
          log.info("Request time: " + elapsed.toString + " ms")
        }
      }
    }
  }

}
