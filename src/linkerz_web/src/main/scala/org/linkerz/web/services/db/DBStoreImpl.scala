/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.db

import org.linkerz.crawler.core.model.WebPage
import org.springframework.data.mongodb.core.MongoOperations
import reflect.BeanProperty

/**
 * The Class WebStoreImpl.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:42 AM
 *
 */

class DBStoreImpl extends DBStore {

  @BeanProperty
  var mongoOperations: MongoOperations = _

  def save(entity: AnyRef) = {
    mongoOperations.save(entity)
    entity
  }
}
