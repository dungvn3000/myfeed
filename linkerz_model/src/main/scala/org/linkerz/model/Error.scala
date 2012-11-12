package org.linkerz.model

import org.bson.types.ObjectId
import java.util.Date

/**
 * The Class Error.
 *
 * @author Nguyen Duc Dung
 * @since 11/13/12 12:39 AM
 *
 */
case class Error
(
  _id: ObjectId = new ObjectId(),
  name: String,
  message: String,
  className: String,
  url: String,
  createDate: Date = new Date
  )

object ErrorDao
