/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.weka.mongodb

import weka.core.{Attribute => WAttribute, Instance, FastVector, Instances}
import org.linkerz.weka.mongodb.annotation.Attribute

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
  def build[T](entityClass: Class[T], datas: List[AnyRef]): Instances = {
    assert(entityClass != null, "Entity class can not be null")
    assert(datas != null, "Data can not be null")
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
    val instances = new Instances(entityClass.getName, attInfo, 0)
    convert(instances, datas)
    instances
  }

  private def load(dataSet: Instances, data: AnyRef): Instance = {
    val instance = new Instance(data.getClass.getDeclaredFields.size)
    data.getClass.getDeclaredFields.foreach(field => {
      val att = field.getAnnotation[Attribute](classOf[Attribute])
      if (att != null) {
        if (att.name.length > 0) {
          val attribute = dataSet.attribute(att.name())
          assert(attribute != null, "The attribute is not match")
          field.setAccessible(true)
          val value = field.get(data)
          if (value.isInstanceOf[String]) {
            instance.setValue(attribute, value.asInstanceOf[String])
          } else if (value.isInstanceOf[Double]) {
            instance.setValue(attribute, value.asInstanceOf[Double])
          }
        }
      }
    })
    instance
  }

  private def convert(dataSet: Instances, datas: List[AnyRef]) {
    datas.foreach(data => {
      load(dataSet, data)
    })
  }

}
