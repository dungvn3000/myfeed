package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import org.linkerz.model.FeedGroup
import com.mongodb.casbah.Imports._

/**
 * The Class FeedGroupDao.
 *
 * @author Nguyen Duc Dung
 * @since 1/3/13 3:16 PM
 *
 */
object FeedGroupDao extends SalatDAO[FeedGroup, ObjectId](mongo("feedgroup"))  {

}
