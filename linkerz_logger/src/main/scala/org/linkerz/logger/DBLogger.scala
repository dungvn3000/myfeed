package org.linkerz.logger

import org.linkerz.model.{LoggingDao, Logging}

/**
 * The Class DBLogger.
 *
 * @author Nguyen Duc Dung
 * @since 11/19/12 8:30 AM
 *
 */
trait DBLogger {

  val clazz = getClass

  def storeError(msg: String) {
    LoggingDao.save(Logging(
      message = msg,
      className = clazz.getName,
      logType = "error"
    ))
  }

  def storeError(msg: String, url: String, t: => Throwable) {
    LoggingDao.save(Logging(
      message = msg,
      className = clazz.getName,
      logType = "error",
      url = Some(url),
      exceptionClass = Some(t.getClass.getName)
    ))
  }

  def storeError(msg: String, t: => Throwable) {
    LoggingDao.save(Logging(
      message = msg,
      className = clazz.getName,
      logType = "error",
      exceptionClass = Some(t.getClass.getName)
    ))
  }

  def storeWarn(msg: String) {
    LoggingDao.save(Logging(
      message = msg,
      className = clazz.getName,
      logType = "warn"
    ))
  }

}
