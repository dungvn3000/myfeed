package org.linkerz.model

import org.joda.time.DateTime
import java.util.UUID

/**
 * The Class Logging.
 *
 * @author Nguyen Duc Dung
 * @since 11/13/12 12:39 AM
 *
 */
case class Logging(
                    id: String = UUID.randomUUID().toString,
                    message: String,
                    className: String,
                    exceptionClass: Option[String] = None,
                    stackTrace: Option[String] = None,
                    logType: String = LogType.Error.toString,
                    category: String = LogCategory.System.toString,
                    url: Option[String] = None,
                    createDate: DateTime = new DateTime
                    )

object LogCategory extends Enumeration("crawling", "system") {
  type LogCategory = Value
  val Crawling, System = Value
}

object LogType extends Enumeration("error", "info", "warn") {
  type LogType = Value
  val Error, Warn, Info = Value
}