package vn.myfeed

import com.mongodb.casbah.{MongoURI, MongoConnection}
import core.conf.AppConfig
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers

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

  lazy val mongo = MongoConnection(MongoURI(AppConfig.mongoUri))(AppConfig.mongoDb)

  RegisterJodaTimeConversionHelpers()

}
