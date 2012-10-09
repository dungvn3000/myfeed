/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.example.weka

import weka.filters.unsupervised.attribute.StringToWordVector
import collection.mutable.ListBuffer
import org.linkerz.weka.mongodb.JavaBeanBuilder
import weka.filters.Filter
import weka.classifiers.trees.J48
import weka.classifiers.bayes.{NaiveBayesUpdateable, NaiveBayes}

/**
 * The Class StringToWordVectorExample.
 *
 * @author Nguyen Duc Dung
 * @since 9/5/12 10:12 PM
 *
 */
object StringToWordVectorExample extends App {

  val userLike1 = new UserLike
  userLike1.title = "Rùa hiếm của Việt Nam ra đời tại Anh"
  userLike1.like = "yes"

  val userLike2 = new UserLike
  userLike2.title = "Lộ diện Honda CBR 250R 2013"
  userLike2.like = "no"

  val userLike3 = new UserLike
  userLike3.title = "Gian nan như người mẫu xuất ngoại"
  userLike3.like = "no"

  val userLike4 = new UserLike
  userLike4.title = "Clip người phụ nữ miền Trung 'đổi' giọng Hà Nội sau tai nạn"
  userLike4.like = "yes"

  val userLikes = new ListBuffer[UserLike]

  userLikes += userLike1
  userLikes += userLike2
  userLikes += userLike3
  userLikes += userLike4

  val train = JavaBeanBuilder.build(classOf[UserLike], userLikes.toList)

  val filter = new StringToWordVector
  filter.setInputFormat(train)

  val dataFiltered = Filter.useFilter(train, filter)
  dataFiltered.setClassIndex(0)

  val classifier = new J48()
  classifier.buildClassifier(dataFiltered)


  val userLike0 = new UserLike
  userLike0.title = "Cuộc sống trong chung cư hoang tàn giữa Sài Gòn"
  val test = JavaBeanBuilder.build(classOf[UserLike], List(userLike0))
  test.setClassIndex(0)

  val pred = classifier.classifyInstance(test.instance(0))

  println("Predict = " + test.instance(0).classAttribute().value(pred.toInt))
}
