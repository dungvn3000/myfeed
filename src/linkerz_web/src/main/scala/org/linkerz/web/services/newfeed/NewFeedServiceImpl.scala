/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.newfeed

import reflect.BeanProperty
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.NewFeed

/**
 * The Class NewFeedServiceImpl.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 9:42 PM
 *
 */

class NewFeedServiceImpl extends NewFeedService {

  @BeanProperty
  var mongoOperations: MongoOperations = _

  def feedList = {
    mongoOperations.findAll(classOf[NewFeed])
  }
}
