package org.linkerz.model

import org.bson.types.ObjectId

/**
 * The Class BlackList.
 *
 * @author Nguyen Duc Dung
 * @since 2/12/13 11:00 AM
 *
 */
case class BlackList(_id: ObjectId, urlRegex: String)
