import org.scalatra._
import javax.servlet.ServletContext
import vn.myfeed.web.MyFeed

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new MyFeed, "/*")
  }
}
