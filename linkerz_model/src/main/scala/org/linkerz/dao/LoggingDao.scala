package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import org.linkerz.model.{Logging, User}
import org.bson.types.ObjectId

/**
 * The Class LoggingDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
object LoggingDao extends SalatDAO[Logging, ObjectId](collection = mongo("logging"))
