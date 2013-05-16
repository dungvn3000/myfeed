package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import org.linkerz.model.User
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.MongoDBObject

/**
 * The Class UserDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
object UserDao extends SalatDAO[User, ObjectId](mongo("user")) {

  def all = find(MongoDBObject.empty).toList

}