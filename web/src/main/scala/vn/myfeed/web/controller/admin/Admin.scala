package vn.myfeed.web.controller.admin

import org.scalatra.ScalatraServlet
import org.scalatra.scalate.{ScalateI18nSupport, ScalateSupport}
import vn.myfeed.web.auth.AuthenticationSupport
import vn.myfeed.web.controller.core.BaseController

/**
 * The Class Admin.
 *
 * @author Nguyen Duc Dung
 * @since 6/6/13 10:00 PM
 *
 */
class Admin extends BaseController {

  get("/") {
    contentType = "text/html"
    jade("/admin.index")
  }

}
