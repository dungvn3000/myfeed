package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import org.linkerz.model.UserFeed
import org.bson.types.ObjectId

/**
 * The Class UserFeedDao.
 *
 * @author Nguyen Duc Dung
 * @since 5/16/13 4:00 PM
 *
 */
object UserFeedDao extends SalatDAO[UserFeed, ObjectId](mongo("userFeed")) {

}
