package org.linkerz.crawl.topology.session

/**
 * The Class RichSession.
 *
 * @author Nguyen Duc Dung
 * @since 12/3/12 1:16 PM
 *
 */
class RichSession(sessions: List[CrawlSession]) {
  def ~>(sessionId: String) = sessions.find(_.id == sessionId)
}

object RichSession {
  implicit def richSessionConvert(sessions: List[CrawlSession]) = new RichSession(sessions)
}
