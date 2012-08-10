/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.mongodb.model

/**
 * The Class LinkParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/9/12, 11:59 PM
 *
 */

class LinkParseData extends LinkerZEntity {

  /**
   * Using simple regex.
   * Only two wildcard
   *
   * (*) and (?)
   */
  var urlRegex: String = _

  var titleSelection: String = _
  var titleAttName: String = _
  var titleMaxLength: Int = _

  var descriptionSelection: String = _
  var descriptionAttName: String = _
  var descriptionMaxLength: Int = _

  var imgSelection: String = _

}
