package vn.myfeed.web.controller.admin

import org.scalatra.ScalatraServlet
import org.scalatra.scalate.ScalateSupport
import vn.myfeed.web.auth.AuthenticationSupport

/**
 * The Class Admin.
 *
 * @author Nguyen Duc Dung
 * @since 6/6/13 10:00 PM
 *
 */
class Admin extends ScalatraServlet with ScalateSupport with AuthenticationSupport {

  get("/") {
    contentType = "text/html"
    jade("/admin.index")
  }

}
