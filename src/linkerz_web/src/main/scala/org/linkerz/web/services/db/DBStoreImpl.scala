/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.db

import org.springframework.data.mongodb.core.MongoOperations
import reflect.BeanProperty
import org.linkerz.mongodb.model.LinkerZEntity
import org.springframework.data.mongodb.core.query.{Criteria, Query}

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

  def delete(entity: LinkerZEntity) {
    mongoOperations.remove(entity)
  }

  def deleteById(id: String, entityClass: Class[_ <: LinkerZEntity]) {
    mongoOperations.findAndRemove(Query.query(Criteria.where("id").is(id)), entityClass)
  }
}
