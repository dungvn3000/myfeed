package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import org.linkerz.model.{Link, User}
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.MongoDBObject

/**
 * The Class LinkDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
object LinkDao extends SalatDAO[Link, ObjectId](collection = mongo("link")) {

  def findByUrl(url: String) = findOne(MongoDBObject("url" -> url))

  def checkAndSave(link: Link) = {
    val result = findOne(MongoDBObject("url" -> link.url))
    if (result.isEmpty) {
      save(link)
      true
    } else false
  }
}
