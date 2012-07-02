package org.linkerz.crawler.core.model

/**
 * The Class WebPage.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 11:40 PM
 *
 */

class WebPage {

  var _webLink: WebLink = _
  var _title: String = _
  var _html: String = _

  def webLink = _webLink

  def title = _title

  def html = _html

  def webLink_=(webLink: WebLink) {
    _webLink = webLink
  }

  def title_=(title: String) {
    _title = title
  }

  def html_=(html: String) {
    _html = html
  }
}
