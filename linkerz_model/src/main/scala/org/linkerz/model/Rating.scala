/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model

import org.bson.types.ObjectId

/**
 * Rating stand for Care the link or Like the comment.
 * @param _id
 * @param targetId the id of link or comment base on targetType
 * @param userId
 * @param targetType 0 if Care, 1 if Comment
 */
case class Rating(
                   _id: ObjectId = new ObjectId,
                   targetId: ObjectId,
                   userId: ObjectId,
                   targetType: Int
                   ) extends BaseModel(_id)

