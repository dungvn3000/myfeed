/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.pages.admin

import org.linkerz.mongodb.model.NewFeed
import org.apache.tapestry5.annotations.{SetupRender, Property}
import org.linkerz.web.services.newfeed.NewFeedService
import org.apache.tapestry5.ioc.annotations.Inject
import org.linkerz.web.services.db.DBStore

/**
 * The Class FeedList.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 9:11 PM
 *
 */

class FeedList {

  @Property
  var feeds: java.util.List[NewFeed] = _

  @Property
  var feed: NewFeed = new NewFeed

  @Inject
  var feedService: NewFeedService = _

  @Inject
  var db: DBStore = _

  @SetupRender
  def initializeValue() {
    feeds = feedService.feedList
  }

  def onSubmit() {
    feed.id = null
    feed.enable = true
    db.save(feed)
  }

  def onActionFromDeleteBtn(id: String) {
    println("feed = " + id)
    db.deleteById(id, classOf[NewFeed])
  }
}
