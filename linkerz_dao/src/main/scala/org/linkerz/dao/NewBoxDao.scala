package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.commons.MongoDBObject
import collection.mutable.ListBuffer
import com.mongodb.casbah.Imports._
import org.linkerz.model.Link
import org.linkerz.model.NewBox
import java.util.Date

/**
 * The Class NewBoxDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
object NewBoxDao extends SalatDAO[NewBox, ObjectId](mongo("newbox")) {

  def getLastTime(userId: ObjectId): Option[Date] = {
    val newBox = find(MongoDBObject.empty).sort(MongoDBObject("createdDate" -> -1)).limit(1).toList
    if (!newBox.isEmpty) {
      val linkId = newBox.head.linkId
      val link = LinkDao.findOneById(linkId)
      if (link.isDefined) {
        return Some(link.get.indexDate)
      }
    }
    None
  }

  def findByUserId(userId: ObjectId): List[Link] = {
    val newBox = find(MongoDBObject("userId" -> userId)).toList
    if (!newBox.isEmpty) {
      val linkIds = newBox.map(_.linkId)
      val links = LinkDao.find(MongoDBObject("_id" -> MongoDBObject("$in" -> linkIds))).toList
      return links
    }
    Nil
  }

  def getUserClicked(userId: ObjectId) = {
    val clicks = find(MongoDBObject("userId" -> userId)).filter(_.click)
    val links = new ListBuffer[Link]
    clicks.foreach(click => {
      val link = LinkDao.findOneById(click.linkId).getOrElse(throw new Exception("Some thing goes worng, can't find the link id"))
      links += link
    })
    links.toList
  }

  def isUserClicked(userId: ObjectId, linkId: ObjectId) = !find(
    MongoDBObject(
      "userId" -> userId,
      "linkId" -> linkId
    )).isEmpty
}