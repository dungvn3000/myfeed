/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.weka.mongodb

import org.scalatest.FunSuite
import collection.mutable.ListBuffer
import weka.classifiers.trees.J48
import weka.classifiers.Evaluation

/**
 * The Class TestJavaBeanBuilder.
 *
 * @author Nguyen Duc Dung
 * @since 8/7/12, 3:47 AM
 *
 */

class TestJavaBeanBuilder extends FunSuite {

  test("testBuilder") {
    val weathers = new ListBuffer[Weather]
    for (i <- 1 to 10) {
      val weather = new Weather
      weather.outlook = "sunny"
      weather.temperature = 85
      weather.humidity = 85
      weather.windy = "false"
      weather.play = "yes"

      weathers += weather
    }

    val train = JavaBeanBuilder.build(classOf[Weather], weathers.toList)
    train.setClassIndex(train.numAttributes() - 1)
    val cls = new J48
    cls.buildClassifier(train)

    val weather = new Weather
    weather.outlook = "sunny"
    weather.temperature = 85
    weather.humidity = 85
    weather.windy = "false"
    weather.play = "no"

    val weatherTest = List[Weather](weather)

    val test = JavaBeanBuilder.build(classOf[Weather], weatherTest)
    test.setClassIndex(test.numAttributes() - 1)

    val evaluation = new Evaluation(train)
    evaluation.evaluateModel(cls, test)

    println(evaluation.toSummaryString)
  }


  test("testPredic") {
    val weathers = new ListBuffer[Weather]
    for (i <- 1 to 10) {
      val weather = new Weather
      weather.outlook = "sunny"
      weather.temperature = 85
      weather.humidity = 85
      weather.windy = "false"
      weather.play = "yes"

      weathers += weather
    }

    val train = JavaBeanBuilder.build(classOf[Weather], weathers.toList)
    train.setClassIndex(train.numAttributes() - 1)
    val cls = new J48
    cls.buildClassifier(train)

    val weather = new Weather
    weather.outlook = "sunny"
    weather.temperature = 85
    weather.humidity = 85
    weather.windy = "false"

    val weatherTest = List[Weather](weather)

    val test = JavaBeanBuilder.build(classOf[Weather], weatherTest)
    test.setClassIndex(test.numAttributes() - 1)

    val predict = cls.classifyInstance(test.instance(0))

    assert(test.classAttribute().value(predict.toInt) == "yes")

  }
}
