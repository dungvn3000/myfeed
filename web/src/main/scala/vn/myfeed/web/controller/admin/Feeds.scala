package vn.myfeed.web.controller.admin

import vn.myfeed.web.controller.core.BaseController

/**
 * The Class Feeds.
 *
 * @author Nguyen Duc Dung
 * @since 6/6/13 11:35 PM
 *
 */
class Feeds extends BaseController {

  get("/partials/:view") {
    contentType="text/html"
    val viewName = params.getOrElse("view", halt())

    println(messages("label.feedAdd"))

    jade(s"/feed/admin.${viewName}")
  }

}
