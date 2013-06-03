package vn.myfeed.web

import org.scalatra.auth.{ScentryStrategy, ScentrySupport, ScentryConfig}
import org.scalatra.ScalatraBase
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}


class OurBasicAuthStrategy(protected val app: ScalatraBase) extends ScentryStrategy[User] {
  def authenticate()(implicit request: HttpServletRequest, response: HttpServletResponse) = {
    println("Hello")
    val username = app.params.getOrElse("username", "")
    if (username == "dungvn3000") {
      Some(User(username))
    } else None
  }
}

trait AuthenticationSupport extends ScentrySupport[User] {
  self: ScalatraBase =>
  protected def fromSession = {
    case id: String => User(id)
  }

  protected def toSession = {
    case user: User => user.id
  }

  override protected def registerAuthStrategies {
    scentry.register("default", app => new OurBasicAuthStrategy(app))
  }

  protected def scentryConfig = (new ScentryConfig {}).asInstanceOf[ScentryConfiguration]
}

case class User(id: String)
