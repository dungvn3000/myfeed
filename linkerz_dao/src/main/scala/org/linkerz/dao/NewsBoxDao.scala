package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.commons.MongoDBObject
import collection.mutable.ListBuffer
import com.mongodb.casbah.Imports._
import org.linkerz.model.Link
import org.linkerz.model.NewsBox

/**
 * The Class NewBoxDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
object NewsBoxDao extends SalatDAO[NewsBox, ObjectId](mongo("newsbox")) {

  def isExist(linkId: ObjectId) = {
    findOne(MongoDBObject("linkId" -> linkId)).isDefined
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
    val clicks = find(MongoDBObject(
      "userId" -> userId,
      "click" -> true
    ))
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
      "linkId" -> linkId,
      "click" -> true
    )).isEmpty
}