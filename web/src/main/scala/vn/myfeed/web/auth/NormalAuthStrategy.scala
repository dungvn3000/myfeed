package vn.myfeed.web.auth

import org.scalatra.ScalatraBase
import org.scalatra.auth.ScentryStrategy
import vn.myfeed.model.User
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}

/**
 * The Class NormalAuthStrategy.
 *
 * @author Nguyen Duc Dung
 * @since 6/4/13 6:25 AM
 *
 */
class NormalAuthStrategy(protected val app: ScalatraBase) extends ScentryStrategy[User] {
  def authenticate()(implicit request: HttpServletRequest, response: HttpServletResponse) = {
    val username = app.params.getOrElse("username", "")
    val password = app.params.getOrElse("password", "")
    User.findByUsername(username).filter(_.password == password)
  }
}
