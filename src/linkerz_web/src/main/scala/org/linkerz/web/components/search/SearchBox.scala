/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.components.search

import org.apache.tapestry5.annotations.{Persist, Component, Property}
import org.apache.tapestry5.corelib.components.{Form, TextField}
import org.linkerz.crawler.core.model.WebUrl
import org.linkerz.crawler.core.fetcher.Fetcher

/**
 * The Class SearchBox.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 2:00 AM
 *
 */

class SearchBox {

  @Persist
  @Property
  private var keyWord: String = _

  @Persist
  @Property
  private var webTitle: String = _

  @Component
  private var txtSearch: TextField = _

  @Component
  private var search: Form = _

  def onSubmit() {
    println(keyWord)
    val fetch = new Fetcher
    val result = fetch.fetch(new WebUrl(keyWord))
    println(result.webPage.title)
    webTitle = result.webPage.title
  }
}
