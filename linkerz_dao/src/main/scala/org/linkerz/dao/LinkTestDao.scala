package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.Imports._
import org.linkerz.model.News

/**
 * The dao is using for persistent link come from a unconfirmed feed.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
object LinkTestDao extends SalatDAO[News, ObjectId](mongo("link_test")) {

  def findByUrl(url: String) = findOne(MongoDBObject("url" -> url))

  def checkAndSave(link: News) = {
    //For testing purpose, we should check url only when save a link.
    val result = findOne(MongoDBObject("url" -> link.url))

    if (result.isEmpty) {
      save(link)
      Some(link)
    } else None
  }

}
