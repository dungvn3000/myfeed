package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.Imports._
import org.linkerz.model.Link

/**
 * The dao is using for persistent link come from a unconfirmed feed.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
object LinkTestDao extends SalatDAO[Link, ObjectId](mongo("link_test")) {

  def findByUrl(url: String) = findOne(MongoDBObject("url" -> url))

  def checkAndSave(link: Link) = {
    val result = findOne(MongoDBObject(
      "$or" -> Array(
        MongoDBObject("url" -> link.url),
        MongoDBObject("title" -> link.title)
      )
    ))

    if (result.isEmpty) {
      save(link)
      Some(link)
    } else None
  }

}
