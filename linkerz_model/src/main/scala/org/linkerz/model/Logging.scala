package org.linkerz.model

import org.bson.types.ObjectId
import java.util.Date

/**
 * The Class Logging.
 *
 * @author Nguyen Duc Dung
 * @since 11/13/12 12:39 AM
 *
 */
case class Logging
(
  _id: ObjectId = new ObjectId(),
  name: String,
  message: String,
  className: String,
  url: String,
  createDate: Date = new Date
  )

object LoggingDao
