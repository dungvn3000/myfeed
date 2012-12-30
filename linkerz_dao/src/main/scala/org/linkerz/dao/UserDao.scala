package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import org.linkerz.model.User
import org.bson.types.ObjectId
import com.mongodb.casbah.MongoCollection

/**
 * The Class UserDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
class UserDao(override val collection: MongoCollection) extends SalatDAO[User, ObjectId](collection) {

}

object UserDao extends UserDao(mongo("user"))