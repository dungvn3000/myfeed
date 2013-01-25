package org.linkerz

import com.mongodb.casbah.MongoConnection
import core.conf.AppConfig

/**
 * The Class package.
 *
 * @author Nguyen Duc Dung
 * @since 11/4/12 1:19 PM
 *
 */
package object dao {

  import com.novus.salat.global._

  implicit val context = ctx

  lazy val mongo = MongoConnection(AppConfig.mongoHost)(AppConfig.mongoDb)

}
