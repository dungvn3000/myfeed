package org.linkerz.crawler.core.model

/**
 * The Class WebLink.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 11:39 PM
 *
 */

class WebLink {

  var _url: String = _

  def this(url: String) {
    this()
    _url = url
  }

  def url = _url

  def url_=(url: String) {
    _url = url
  }

}
