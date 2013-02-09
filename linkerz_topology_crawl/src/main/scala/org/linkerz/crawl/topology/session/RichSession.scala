package org.linkerz.crawl.topology.session

import java.util.UUID

/**
 * This class is a session wrapper using for adding more functional for a session list.
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
      false
    } else true
  })

}

object RichSession {
  implicit def richSessionConvert(sessions: List[CrawlSession]) = new RichSession(sessions)
}
