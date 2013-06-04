package vn.myfeed.web.controller

import vn.myfeed.web.auth.AuthenticationSupport
import vn.myfeed.web.MyfeedStack
import org.json4s.{Formats, DefaultFormats}
import org.scalatra.json._

class MyFeed extends MyfeedStack with AuthenticationSupport with NativeJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  before("/login") {
    println("dung ne")
  }

  get("/") {
    contentType = formats("json")
    List(
      Test("dung"),
      Test("trang")
    )
  }

  get("/hello") {
    if (isAuthenticated) {
      "what's up? " + user.id
    } else {
      redirect("/login")
    }
  }

  get("/login") {
    if (authenticate.isDefined) {
      redirect("/")
    } else {
      "wrong username"
    }
  }

  get("/logout") {
    logOut
    if (!isAuthenticated) redirect("/login")
  }

}

case class Test(name: String)
