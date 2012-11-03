package org.linkerz

import com.mongodb.casbah.MongoConnection

/**
 * The Class package.
 *
 * @author Nguyen Duc Dung
 * @since 11/3/12 5:06 PM
 *
 */
package object model {

  var mongo = MongoConnection()("linkerz")

}
