package vn.myfeed.web.auth

import org.scalatra.auth.{ScentrySupport, ScentryConfig}
import org.scalatra.ScalatraBase
import vn.myfeed.model.User
import org.bson.types.ObjectId

trait AuthenticationSupport extends ScentrySupport[User] {
  self: ScalatraBase =>
  protected def fromSession = {
    case id: String => User.findOneById(new ObjectId(id)).get
  }

  protected def toSession = {
    case user: User => user.id
  }

  override protected def registerAuthStrategies {
    scentry.register("default", app => new NormalAuthStrategy(app))
  }

  protected def scentryConfig = (new ScentryConfig {}).asInstanceOf[ScentryConfiguration]
}