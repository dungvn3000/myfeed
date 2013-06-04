package vn.myfeed

import com.mongodb.casbah.{MongoURI, MongoConnection}
import vn.myfeed.core.conf.AppConfig
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers

/**
 * The Class package.
 *
 * @author Nguyen Duc Dung
 * @since 6/4/13 5:37 AM
 *
 */
package object model {

  import com.novus.salat.global._

  implicit val context = ctx

  lazy val mongo = MongoConnection(MongoURI(AppConfig.mongoUri))(AppConfig.mongoDb)

  RegisterJodaTimeConversionHelpers()

}
