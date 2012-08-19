/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.servlet

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import org.apache.commons.lang.StringUtils
import org.springframework.web.context.support.WebApplicationContextUtils
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.{Criteria, Query}
import org.linkerz.mongodb.model.Link

/**
 * The Class DownloadServlet.
 *
 * @author Nguyen Duc Dung
 * @since 8/19/12, 12:30 PM
 *
 */
class DownloadServlet extends HttpServlet {

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    var fileId = req.getPathInfo
    if (StringUtils.isNotBlank(fileId)) {
      fileId = fileId.replaceAll("/","")
      val springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext)
      val mongoOperations = springContext.getBean("mongoTemplate", classOf[MongoOperations])
      val link = mongoOperations.findOne(Query.query(Criteria.where("id").is(fileId)), classOf[Link])
      if (link != null) {
        resp.setContentType("image/jpeg")
        resp.getOutputStream.write(link.featureImage)
      } else {
        println("Image not found " + fileId)
      }
    }
  }
}
