/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.weka.mongodb

import weka.core.{FastVector, Instances}
import org.linkerz.weka.mongodb.annotation.Attribute
import weka.core.{Attribute => WAttribute}

/**
 * The Class MongoDbBuilder.
 *
 * @author Nguyen Duc Dung
 * @since 8/7/12, 3:43 AM
 *
 */

object MongoDbBuilder {

  /**
   * Build an empty instances base on entity class.
   * @param entityClass
   * @return
   */
  def build[T](entityClass: Class[T]): Instances = {
    assert(entityClass != null, "Entity class can not be null")
    val attInfo = new FastVector
    entityClass.getDeclaredFields.foreach(field => {
      val att = field.getAnnotation[Attribute](classOf[Attribute])
      if (att != null) {
        if (att.values.isEmpty) {
          val attribute = new WAttribute(att.name())
          attInfo.addElement(attribute)
        } else {
          val values = new FastVector
          att.values.foreach(value => values.addElement(value))
          val attribute = new WAttribute(att.name(), values)
          attInfo.addElement(attribute)
        }
      }
    })
    assert(attInfo.size > 0, "No attribute for this class")
    new Instances(entityClass.getName, attInfo, 0)
  }

}
