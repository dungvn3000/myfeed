package org.linkerz.crawler.bot.parser

import org.linkerz.crawler.bot.parser.core.{ParserData, NewsParser}
import org.linkerz.model.NewFeed

/**
 * The Class ActionVnParser.
 *
 * @author Nguyen Duc Dung
 * @since 11/13/12 8:10 PM
 *
 */
class ActionVnParser extends NewsParser {

  val feed = NewFeed(
    name = "action.vn",
    group = "startup",
    url = "action.vn",
    enable = true,
    urlRegex = "*/action.vn/*",
    titleSelection = ".contentpaneopen h1.contentheading",
    contentSelection = ".contentpaneopenlink",
    imgSelection = ".contentpaneopenlink img",
    defaultImgUrl = "http://www.action.vn/images/stories/144x60xlogo.png.pagespeed.ic.F333qkSp3N.png"
  )

  data = feed

}
