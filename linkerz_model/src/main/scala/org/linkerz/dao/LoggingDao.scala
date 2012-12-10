package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.Imports._
import org.linkerz.model.Logging

/**
 * The Class LoggingDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
class LoggingDao(override val collection: MongoCollection) extends SalatDAO[Logging, ObjectId](collection) {

}

object LoggingDao extends LoggingDao(mongo("logging"))