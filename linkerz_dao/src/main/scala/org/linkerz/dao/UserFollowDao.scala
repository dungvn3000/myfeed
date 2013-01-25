package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import org.linkerz.model.UserFollow
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.MongoDBObject

/**
 * The Class UserFollowDao.
 *
 * @author Nguyen Duc Dung
 * @since 1/3/13 4:09 PM
 *
 */
object UserFollowDao extends SalatDAO[UserFollow, ObjectId](mongo("userfollow")) {

  def findUserFollowByUserID(userId: ObjectId) = find(MongoDBObject("userId" -> userId)).toList

}
