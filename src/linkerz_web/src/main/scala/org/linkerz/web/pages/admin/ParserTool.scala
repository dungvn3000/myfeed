/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.pages.admin

import org.apache.tapestry5.annotations.{Persist, Property}
import org.linkerz.crawler.bot.parser.LinkerZParser
import org.linkerz.mongodb.model.LinkParseData


/**
 * The Class Admin.
 *
 * @author Nguyen Duc Dung
 * @since 8/10/12, 2:54 AM
 *
 */

class ParserTool {

  @Persist
  @Property
  var urlRegex: String = _

  @Persist
  @Property
  var titleSelection: String = _

  @Persist
  @Property
  var titleAttName: String = _

  @Persist
  @Property
  var titleMaxLength: Int = _

  @Persist
  @Property
  var descriptionSelection: String = _

  @Persist
  @Property
  var descriptionAttName: String = _

  @Persist
  @Property
  var descriptionMaxLength: Int = _

  @Persist
  @Property
  var contentSelection: String = _

  @Persist
  @Property
  var contentAttName: String = _

  @Persist
  @Property
  var contentMaxLength: Int = _

  @Persist
  @Property
  var imgSelection: String = _

  @Persist
  @Property
  var urlTest: String = _


  def onSubmit() {

    val linkParseData = new LinkParseData
    linkParseData.urlRegex = urlRegex

    linkParseData.titleAttName = titleAttName
    linkParseData.titleMaxLength = titleMaxLength
    linkParseData.titleSelection = titleSelection

    linkParseData.descriptionAttName = descriptionAttName
    linkParseData.descriptionMaxLength = descriptionMaxLength
    linkParseData.descriptionSelection = descriptionSelection

    linkParseData.contentAttName = contentAttName
    linkParseData.contentMaxLength = contentMaxLength
    linkParseData.contentSelection = contentSelection

    linkParseData.imgSelection = imgSelection

    val parser = new LinkerZParser
    parser.parse(urlTest, linkParseData)

  }


}
