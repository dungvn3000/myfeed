package org.linkerz.model

import org.bson.types.ObjectId

/**
 * The Class BlackUrl.
 *
 * @author Nguyen Duc Dung
 * @since 2/12/13 11:00 AM
 *
 */
case class BlackUrl(_id: ObjectId = new ObjectId, urlRegex: String) extends BaseModel(_id)
