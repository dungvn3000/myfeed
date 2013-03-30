package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import org.linkerz.model.UserLink
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.MongoDBObject

/**
 * The Class UserDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
object UserLinkDao extends SalatDAO[UserLink, ObjectId](mongo("userLink")) {

  def findByUserId(userId: ObjectId) = find(MongoDBObject("userId" -> userId)).toList

}