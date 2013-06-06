package vn.myfeed.web.controller.core

import org.scalatra.ScalatraServlet
import org.scalatra.scalate.{ScalateI18nSupport, ScalateSupport}
import vn.myfeed.web.auth.AuthenticationSupport

/**
 * The Class BaseController.
 *
 * @author Nguyen Duc Dung
 * @since 6/6/13 11:28 PM
 *
 */
trait BaseController extends ScalatraServlet with ScalateSupport with AuthenticationSupport with ScalateI18nSupport {



}
