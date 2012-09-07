/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.pages

import org.apache.tapestry5.annotations._
import org.apache.tapestry5.ioc.annotations.Inject
import org.linkerz.web.services.user.UserService
import org.linkerz.mongodb.model.Link
import org.linkerz.web.services.newfeed.NewFeedService
import collection.JavaConversions._

/**
 * The Class Index.
 *
 * @author Nguyen Duc Dung
 * @since 8/2/12, 9:33 PM
 *
 */

class Index {

  @Persist
  @Property
  private var links: java.util.List[Link] = _

  @Property
  private var link: Link = _

  @Inject
  private var userService: UserService = _

  @Inject
  private var newFeedService: NewFeedService = _

  @SetupRender
  def initializeValue() {
    links = newFeedService.linkList
  }

  def onLinkClick(id: String) {
    userService.userClick("dungvn3000", id, links.toList)
  }
}
