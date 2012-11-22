package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import org.linkerz.model.{NewFeed, User}
import org.bson.types.ObjectId

/**
 * The Class NewFeedDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
object NewFeedDao extends SalatDAO[NewFeed, ObjectId](collection = mongo("newFeed")) {

}
