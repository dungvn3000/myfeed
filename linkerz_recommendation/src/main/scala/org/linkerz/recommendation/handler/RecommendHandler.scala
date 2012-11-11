package org.linkerz.recommendation.handler

import org.linkerz.job.queue.core.{Job, Handler}
import org.linkerz.recommendation.job.RecommendJob
import org.linkerz.model.{LinkDao, NewBoxDao, NewBox, UserDao}
import com.mongodb.casbah.commons.MongoDBObject
import org.linkerz.recommendation.Recommendation
import org.bson.types.ObjectId
import grizzled.slf4j.Logging

/**
 * The Class RecommendHandler.
 *
 * @author Nguyen Duc Dung
 * @since 11/11/12 4:37 PM
 *
 */
class RecommendHandler extends Handler[RecommendJob] with Logging {

  def accept(job: Job) = job.isInstanceOf[RecommendJob]

  protected def doHandle(job: RecommendJob) {
    val users = UserDao.find(MongoDBObject.empty)
    val links = LinkDao.find(MongoDBObject("parsed" -> true)).toList.sortWith((link1, link2) => {
      link1.indexDate.compareTo(link2.indexDate) > 0
    })
    users.foreach(user => {
      val userBox = NewBoxDao.findByUserId(user._id)
      if (!userBox.isEmpty) {
        val clickedLinks = NewBoxDao.getUserClicked(user._id)
        if (!clickedLinks.isEmpty) {
          val newLinks = links.filter(!userBox.contains(_)).toList
          Recommendation.buildScoreTable(clickedLinks, newLinks, minScore = -0.3).foreach(r => r match {
            case (link1Id, link2Id, score) => {
              NewBoxDao.save(NewBox(
                userId = user._id,
                linkId = new ObjectId(link2Id)
              ))
              info("Send to " + user.userName + " " + link2Id + " " + score)
            }
          })
        }
      }
    })
  }
}
