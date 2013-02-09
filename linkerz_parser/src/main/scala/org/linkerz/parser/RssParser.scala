package org.linkerz.parser

import java.io.InputStream
import com.sun.syndication.io.{SyndFeedInput, XmlReader}

/**
 * The Class RssParser.
 *
 * @author Nguyen Duc Dung
 * @since 2/9/13 10:18 PM
 *
 */
class RssParser {

  def parse(input : InputStream) = {
    val xmlReader = new XmlReader(input)
    val feed = new SyndFeedInput().build(xmlReader)
    feed
  }

}
