package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import org.linkerz.model.{Link, NewBox, User}
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.MongoDBObject
import collection.mutable.ListBuffer

/**
 * The Class NewBoxDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
object NewBoxDao extends SalatDAO[NewBox, ObjectId](collection = mongo("newBox")) {

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

}
