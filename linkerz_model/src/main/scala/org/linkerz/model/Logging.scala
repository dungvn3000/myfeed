package org.linkerz.model

import org.bson.types.ObjectId
import java.util.Date
import com.novus.salat.dao.SalatDAO

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
  message: String,
  className: String,
  exceptionClass: Option[String] = None,
  logType: String,
  url: Option[String] = None,
  createDate: Date = new Date
  ) {

  def id = _id.toString

}


