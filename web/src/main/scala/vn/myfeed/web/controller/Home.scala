package vn.myfeed.web.controller

import vn.myfeed.web.controller.core.BaseController

class Home extends BaseController {

  get("/") {
    contentType="text/html"
    jade("index")
  }


}

