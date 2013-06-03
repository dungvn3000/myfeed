package vn.myfeed.web


class MyFeed extends MyfeedStack with AuthenticationSupport {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }

  get("/hello") {
    if (isAuthenticated) {
      "what's up? " + user.id
    } else {
      redirect("/login")
    }
  }

  get("/login") {
    if(authenticate.isDefined) {
      redirect("/")
    } else {
      "wrong username"
    }
  }

  get("/logout") {
    logOut
    if(isAuthenticated) redirect("/login")
  }

}
