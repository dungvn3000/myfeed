/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.downloader

import com.ning.http.client.filter.{FilterContext, ResponseFilter}
import com.ning.http.client.RequestBuilder
import org.apache.http.HttpStatus

/**
 * The Class DefaultResponseFilter.
 *
 * @author Nguyen Duc Dung
 * @since 8/16/12, 4:40 AM
 *
 */

class DefaultResponseFilter extends ResponseFilter {
  def filter(ctx: FilterContext[_]): FilterContext[_] = {
    val statusCode = ctx.getResponseStatus.getStatusCode
    if (statusCode == HttpStatus.SC_MOVED_TEMPORARILY
      || statusCode == HttpStatus.SC_MOVED_PERMANENTLY) {
      return new FilterContext.FilterContextBuilder(ctx)
        .request(new RequestBuilder()
        .setUrl("http://google.com").build())
        .build()
    }
    ctx
  }
}
