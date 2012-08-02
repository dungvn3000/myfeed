/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.db

import org.linkerz.crawler.core.model.WebPage

/**
 * The Class WebStore.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:41 AM
 *
 */

trait WebStore {

  def save(webPage: WebPage): WebPage

  def loadAll(): java.util.List[WebPage]

}
