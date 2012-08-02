/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.pages

import org.apache.tapestry5.annotations._
import org.linkerz.web.services.db.WebStore
import org.apache.tapestry5.ioc.annotations.Inject
import org.linkerz.crawler.core.model.WebPage

/**
 * The Class Index.
 *
 * @author Nguyen Duc Dung
 * @since 8/2/12, 9:33 PM
 *
 */

class Index {

  @Property
  private var result: java.util.List[WebPage] = _

  @Property
  private var webPage: WebPage = _

  @Inject
  private var webStore: WebStore = _


  @SetupRender
  def initializeValue() {
    result = webStore.loadAll()
  }

}
