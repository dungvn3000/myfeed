package org.linkerz.crawl.topology.session

import java.util.UUID

/**
 * The Class RichSession.
 *
 * @author Nguyen Duc Dung
 * @since 12/3/12 1:16 PM
 *
 */
class RichSession(sessions: List[CrawlSession]) {

  /**
   * Find a session by id.
   * @param sessionId
   * @return
   */
  def ~>(sessionId: UUID) = sessions.find(_.id == sessionId)

  /**
   * End a session and remove it form the list
   * @param sessionId
   * @return
   */
  def end(sessionId: UUID) = sessions.filter(session => {
    if (session.id == sessionId) {
      session.endSession()
      false
    } else true
  })

}

object RichSession {
  implicit def richSessionConvert(sessions: List[CrawlSession]) = new RichSession(sessions)
}
