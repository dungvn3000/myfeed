package org.linkerz.logger

import org.linkerz.dao.LoggingDao
import org.linkerz.model.{LogCategory, LogType, Logging}

/**
 * The Class DBLogger.
 *
 * @author Nguyen Duc Dung
 * @since 11/19/12 8:30 AM
 *
 */
trait DBLogger {

  val clazz = getClass

  def storeError(msg: String, category: LogCategory.Value) {
    LoggingDao.save(Logging(
      message = msg,
      category = category.toString,
      className = clazz.getName
    ))
  }

  def storeError(msg: String, url: String, category: LogCategory.Value) {
    LoggingDao.save(Logging(
      message = msg,
      className = clazz.getName,
      category = category.toString,
      url = Some(url)
    ))
  }

  def storeError(msg: String, url: String, exception: Throwable, category: LogCategory.Value) {
    LoggingDao.save(Logging(
      message = msg,
      className = clazz.getName,
      category = category.toString,
      url = Some(url),
      exceptionClass = Some(exception.getClass.getName)
    ))
  }

  def storeError(msg: String, exception: Throwable, category: LogCategory.Value) {
    LoggingDao.save(Logging(
      message = msg,
      className = clazz.getName,
      category = category.toString,
      exceptionClass = Some(exception.getClass.getName)
    ))
  }

  def storeWarn(msg: String, category: LogCategory.Value) {
    LoggingDao.save(Logging(
      message = msg,
      className = clazz.getName,
      category = category.toString,
      logType = LogType.Warn.toString
    ))
  }

}
