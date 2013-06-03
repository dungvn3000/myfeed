/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model

import org.bson.types.ObjectId

/**
 * The Class Feeds.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 7:47 PM
 *
 */

case class Feed(
                 _id: ObjectId = new ObjectId,
                 name: String,
                 url: String,
                 topic: Option[String] = None
                 ) extends BaseModel(_id)

