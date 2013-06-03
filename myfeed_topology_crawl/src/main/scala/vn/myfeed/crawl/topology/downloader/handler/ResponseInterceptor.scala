package vn.myfeed.crawl.topology.downloader.handler

import org.apache.http.{HttpResponse, HttpResponseInterceptor}
import org.apache.http.protocol.HttpContext
import org.apache.http.client.entity.GzipDecompressingEntity

/**
 * The Class ResponseInterceptor.
 *
 * @author Nguyen Duc Dung
 * @since 5/9/13 6:44 AM
 *
 */
class ResponseInterceptor extends HttpResponseInterceptor {
  def process(response: HttpResponse, context: HttpContext) {
    val entity = response.getEntity
    val contentEncoding = entity.getContentEncoding
    if (contentEncoding != null) {
      val codecs = contentEncoding.getElements
      for (codec <- codecs) {
        if ("gzip".equalsIgnoreCase(codec.getName)) {}
        response.setEntity(new GzipDecompressingEntity(response.getEntity))
        return
      }
    }
  }
}
