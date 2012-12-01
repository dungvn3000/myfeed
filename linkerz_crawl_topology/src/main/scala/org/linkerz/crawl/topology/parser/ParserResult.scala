/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.parser

import org.linkerz.crawl.topology.model.WebPage
import collection.mutable.ListBuffer

/**
 * The Class ParserResult.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:10 AM
 *
 */

case class ParserResult(webPage: WebPage) {

  private var _error = new ListBuffer[String]
  private var _info = new ListBuffer[String]

  var code: ParserResult.Status = ParserResult.DONE

  def error = _error

  def info = _info

  def info(msg: String) {
    _info += msg
  }

  def error(msg: String) {
    code = ParserResult.ERROR
    _error += msg
  }

}

object ParserResult extends Enumeration {
  type Status = Value
  val DONE, SKIP, ERROR = Value
}
