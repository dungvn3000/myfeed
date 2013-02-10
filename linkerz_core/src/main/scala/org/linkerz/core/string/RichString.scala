package org.linkerz.core.string

import org.apache.commons.lang.StringUtils

/**
 * This class is a wrapper of String, to using Apache StringUtil.
 *
 * @author Nguyen Duc Dung
 * @since 2/10/13 2:40 PM
 *
 */
case class RichString(st: String) {

  def isNotBlank = StringUtils.isNotBlank(st)

  def isBlank = StringUtils.isBlank(st)

  def trimToEmpty = StringUtils.trimToEmpty(st)

}


object RichString {

  implicit def stringToRichString(st: String) = RichString(st)

}