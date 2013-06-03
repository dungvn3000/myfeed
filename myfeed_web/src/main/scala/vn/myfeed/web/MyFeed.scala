package vn.myfeed.web


class MyFeed extends MyfeedStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }

  get("/hello") {
    contentType = "text/html"
    jade("/WEB-INF/templates/views/dung.jade")
  }
  
}
