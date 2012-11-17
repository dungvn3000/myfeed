package org.linkerz

import com.mongodb.casbah.MongoConnection

/**
 * The Class package.
 *
 * @author Nguyen Duc Dung
 * @since 11/4/12 1:19 PM
 *
 */
package object model {

  import com.novus.salat.global._

  implicit val context = ctx

  val mongo = MongoConnection("localhost")("linkerz")

}
