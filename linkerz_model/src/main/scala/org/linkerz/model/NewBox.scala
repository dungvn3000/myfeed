package org.linkerz.model

import org.bson.types.ObjectId
import java.util.Date

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

