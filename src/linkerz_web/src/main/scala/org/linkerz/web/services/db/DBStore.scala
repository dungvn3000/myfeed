/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.db

import org.linkerz.mongodb.model.LinkerZEntity

/**
 * The Class WebStore.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:41 AM
 *
 */

trait DBStore {

  def save(entity: AnyRef): AnyRef

  def deleteById(id: String, entityClass: Class[_ <: LinkerZEntity])

  def delete(entity: LinkerZEntity)

  def findAll(entityClass: Class[_ <: LinkerZEntity]): java.util.List[_ <: LinkerZEntity]
}
