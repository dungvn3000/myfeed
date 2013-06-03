package vn.myfeed.model

import org.bson.types.ObjectId
import org.joda.time.DateTime

/**
 * The Class Logging.
 *
 * @author Nguyen Duc Dung
 * @since 11/13/12 12:39 AM
 *
 */
case class Logging(
                    _id: ObjectId = new ObjectId(),
                    message: String,
                    className: String,
                    exceptionClass: Option[String] = None,
                    stackTrace: Option[String] = None,
                    logType: String = LogType.Error.toString,
                    category: String = LogCategory.System.toString,
                    url: Option[String] = None,
                    createDate: DateTime = DateTime.now()
                    ) extends BaseModel(_id)

object LogCategory extends Enumeration("crawling", "system") {
  type LogCategory = Value
  val Crawling, System = Value
}

object LogType extends Enumeration("error", "info", "warn") {
  type LogType = Value
  val Error, Warn, Info = Value
}