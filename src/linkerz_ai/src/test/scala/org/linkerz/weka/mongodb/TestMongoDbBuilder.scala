/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.weka.mongodb

import org.scalatest.FunSuite
import weka.core.Instance

/**
 * The Class TestMongoDbBuilder.
 *
 * @author Nguyen Duc Dung
 * @since 8/7/12, 3:47 AM
 *
 */

class TestMongoDbBuilder extends FunSuite {

  test("testBuilder") {
    val train = MongoDbBuilder.build(classOf[Weather])
    val instance = new Instance(5)
    instance.setValue(train.attribute(0), "sunny")
    instance.setValue(train.attribute(1), 85)
    instance.setValue(train.attribute(2), 85)
    instance.setValue(train.attribute(3), "false")
    instance.setValue(train.attribute(4), "yes")
    train.add(instance)
  }

}
