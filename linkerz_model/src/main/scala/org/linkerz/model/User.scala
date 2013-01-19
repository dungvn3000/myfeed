package org.linkerz.model

import org.bson.types.ObjectId

/**
 * The Class User.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:05 PM
 *
 */

case class User(id: String, password: String, followDomains: Seq[String] = Nil)