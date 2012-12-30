package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.Imports._
import org.linkerz.model.Feed

/**
 * The Class NewFeedDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
class NewFeedDao(override val collection: MongoCollection) extends SalatDAO[Feed, ObjectId](collection) {

}


object NewFeedDao extends NewFeedDao(mongo("newfeed"))