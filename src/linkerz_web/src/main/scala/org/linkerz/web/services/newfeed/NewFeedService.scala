/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.newfeed

import org.linkerz.mongodb.model.NewFeed

/**
 * The Class NewFeedService.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 9:42 PM
 *
 */

trait NewFeedService {

  def feedList: java.util.List[NewFeed]

}
