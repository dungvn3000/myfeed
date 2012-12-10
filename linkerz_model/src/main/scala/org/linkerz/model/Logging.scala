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
  message: String,
  className: String,
  exceptionClass: Option[String] = None,
  logType: String = LogType.Error.toString,
  category: String = LogCategory.System.toString,
  url: Option[String] = None,
  createDate: Date = new Date
  ) {
  def id = _id.toString
}

object LogCategory extends Enumeration("testcase", "crawling", "system") {
  type LogCategory = Value
  val TestCase, Crawling, System = Value
}

object LogType extends Enumeration("error", "info", "warn") {
  type LogType = Value
  val Error, Warn, Info = Value
}