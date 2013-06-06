package vn.myfeed.web.controller

import vn.myfeed.web.auth.AuthenticationSupport
import org.scalatra.ScalatraServlet
import org.scalatra.scalate.ScalateSupport

class Home extends ScalatraServlet with ScalateSupport with AuthenticationSupport {


  get("/") {
    contentType="text/html"
    jade("index")
  }


}

