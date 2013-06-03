/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model

import org.bson.types.ObjectId
import org.joda.time.DateTime

/**
 *
 * @param _id
 * @param linkId
 * @param userId
 */
case class Comment(
                   _id: ObjectId = new ObjectId,
                   linkId: ObjectId,
                   userId: ObjectId,
                   comment: String,
                   indexDate: DateTime = DateTime.now()
                   ) extends BaseModel(_id)

