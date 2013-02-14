package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.Imports._
import org.linkerz.model.Feed
import com.mongodb.casbah.commons.MongoDBObject

/**
 * The Class FeedDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
object FeedDao extends SalatDAO[Feed, ObjectId](mongo("feed")) {

  def all = find(MongoDBObject("enable" -> true)).toList

}