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
object LoggingDao extends SalatDAO[Logging, ObjectId](mongo("logging")) {

}