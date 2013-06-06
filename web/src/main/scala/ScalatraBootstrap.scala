import org.scalatra._
import javax.servlet.ServletContext
import vn.myfeed.web.controller.admin.{Feeds, Admin}
import vn.myfeed.web.controller.Home

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new Home, "/*")
    context.mount(new Admin, "/admin/*")
    context.mount(new Feeds, "/admin/feed/*")
  }
}
