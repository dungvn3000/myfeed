package org.linkerz.model

import org.bson.types.ObjectId
import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.commons.MongoDBObject
import collection.mutable.ListBuffer
import java.util.Date
import org.linkerz.dao.LinkDao

/**
 * The Class NewBox.
 *
 * @author Nguyen Duc Dung
 * @since 11/11/12 2:25 AM
 *
 */
case class NewBox
(
  _id: ObjectId = new ObjectId(),
  userId: ObjectId,
  linkId: ObjectId,
  click: Boolean = false,
  createdDate: Date = new Date()
  )

