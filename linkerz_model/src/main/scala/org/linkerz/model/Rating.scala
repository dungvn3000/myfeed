/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model

import org.bson.types.ObjectId

case class Rating(
                   _id: ObjectId = new ObjectId,
                   targetId: ObjectId,
                   userId: ObjectId,
                   isLiked: Boolean,
                   targetType: Int
                   ) extends LinkerZModel(_id)

