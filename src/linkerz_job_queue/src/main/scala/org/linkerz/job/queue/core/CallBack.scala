/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.core

/**
 * The Class CallBack.
 *
 * @author Nguyen Duc Dung
 * @since 7/9/12, 1:45 PM
 *
 */

trait CallBack[R] {

  /**
   * Call when the job was done in success.
   * @param source
   * @param result
   */
  def onSuccess(source: Any, result: Option[R])

  /**
   * Call when has anything exception.
   * @param source
   * @param ex
   */
  def onFailed(source: Any, ex: Exception)
}

trait CallBackable[R] {

  /**
   * Getter for callback
   * @return
   */
  def callback: CallBack[R]

  /**
   * Setter for callback
   * @param callback
   */
  def callback_=(callback: CallBack[R])
}