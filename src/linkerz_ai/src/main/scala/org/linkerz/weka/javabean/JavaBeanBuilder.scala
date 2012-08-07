/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.weka.mongodb

import weka.core.{Attribute => WAttribute, Instance, FastVector, Instances}
import org.linkerz.weka.mongodb.annotation.Attribute
import org.linkerz.weka.mongodb.annotation.Attribute.TYPE._

/**
 * The Class JavaBeanBuilder.
 *
 * @author Nguyen Duc Dung
 * @since 8/7/12, 3:43 AM
 *
 */

object JavaBeanBuilder {

  /**
   * Build an empty instances base on entity class.
   * @param entityClass
   * @param datas
   * @return
   */
  def build[T](entityClass: Class[T], datas: List[AnyRef]): Instances = {
    assert(entityClass != null, "Entity class can not be null")
    assert(datas != null, "Data can not be null")
    val attInfo = new FastVector
    entityClass.getDeclaredFields.foreach(field => {
      val att = field.getAnnotation[Attribute](classOf[Attribute])
      if (att != null) {
        var attribute: WAttribute = null
        if (att.attType == NUMBER) {
          attribute = new WAttribute(att.name())
        } else if (att.attType == NOMINAL) {
          val values = new FastVector
          att.values.foreach(value => values.addElement(value))
          attribute = new WAttribute(att.name(), values)
        } else if (att.attType == STRING) {
          //add string type ref from :http://weka.wikispaces.com/Creating+an+ARFF+file
          attribute = new WAttribute(att.name(), null.asInstanceOf[FastVector])
        }

        if (attribute != null) {
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
    dataSet.add(instance)
    instance
  }

  private def convert(dataSet: Instances, datas: List[AnyRef]) {
    datas.foreach(data => {
      load(dataSet, data)
    })
  }

}
