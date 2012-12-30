package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.Imports._
import org.linkerz.model.Link

/**
 * The Class LinkDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
class LinkDao(override val collection: MongoCollection) extends SalatDAO[Link, ObjectId](collection) {

  def findByUrl(url: String) = findOne(MongoDBObject("url" -> url))

  def checkAndSave(link: Link) = {
    val result = findOne(MongoDBObject("url" -> link.url))
    if (result.isEmpty) {
      save(link)
      Some(link)
    } else None
  }
}

object LinkDao extends LinkDao(mongo("link")) {

}
