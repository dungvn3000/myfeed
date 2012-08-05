/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.components.search

import org.apache.tapestry5.annotations.{Component, Property}
import org.apache.tapestry5.corelib.components.{Form, TextField}
import org.linkerz.crawler.core.fetcher.Fetcher
import org.linkerz.web.services.db.DBStore
import org.apache.tapestry5.ioc.annotations.Inject
import org.linkerz.mongodb.model.{Link, User}
import org.linkerz.crawler.core.model.WebUrl
import org.linkerz.web.services.user.UserService

/**
 * The Class SearchBox.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 2:00 AM
 *
 */

class SearchBox {

  @Property
  private var keyWord: String = _

  @Component
  private var txtSearch: TextField = _

  @Component
  private var search: Form = _

  @Inject
  private var dbStore: DBStore = _

  @Inject
  private var userService: UserService = _

  def onSubmit() {
    println(keyWord)
    val fetch = new Fetcher
    val result = fetch.fetch(new WebUrl(keyWord))

    var user = userService.getUser("dung")
    if (user == null) {
      user = new User
      user.userName = "dung"
      user.passWord = "dung"
      dbStore.save(user)
    }

    val link = new Link
    link.userId = user.id
    link.url = keyWord

    dbStore.save(link)
  }
}
