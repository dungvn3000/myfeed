package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import org.linkerz.model.UserNews
import org.bson.types.ObjectId

/**
 * The Class UserFeedDao.
 *
 * @author Nguyen Duc Dung
 * @since 5/16/13 4:00 PM
 *
 */
object UserNewsDao extends SalatDAO[UserNews, ObjectId](mongo("userNews")) {

}
