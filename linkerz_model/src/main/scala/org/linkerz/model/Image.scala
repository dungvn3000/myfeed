package org.linkerz.model

import org.bson.types.ObjectId

/**
 * The Class Image.
 *
 * @author Nguyen Duc Dung
 * @since 3/29/13 7:41 PM
 *
 */
case class Image(_id: ObjectId = new ObjectId(),
                 original: Array[Byte],
                 thumb: Option[Array[Byte]] = None) extends BaseModel(_id)
