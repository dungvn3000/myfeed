/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.language

import detect.vietnamese.WordClean
import org.linkerz.test.spring.SpringContext
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.{Criteria, Query}
import org.linkerz.mongodb.model.WordTuple2

import de.l3s.boilerpipe.util.UnicodeTokenizer

import collection.JavaConverters._
import collection.mutable.ListBuffer

/**
 * The Class GuessWord.
 *
 * @author Nguyen Duc Dung
 * @since 8/8/12, 6:20 PM
 *
 */

object GuessWord extends App with SpringContext {

  val mongoOperations = context.getBean("mongoTemplate", classOf[MongoOperations])


  while (true) {
    print("Type your sentence: ")
    val sd = WordClean.clean(Console.readLine()).toLowerCase
    var time = System.currentTimeMillis

    val words = UnicodeTokenizer.tokenize(sd)

    var guessWord: List[WordTuple2] = Nil
    words.foreach(word => {
      val result = mongoOperations.find(
        Query.query(Criteria.where("word1").is(word).and("count").gte(5)), classOf[WordTuple2])
      guessWord ++= result.asScala.toList
    })

    guessWord = guessWord.sortBy(w => 0 - w.count)

    val detectWord = new ListBuffer[String]
    guessWord.foreach(tuple2 => {
      detectWord += tuple2.word1 + " " + tuple2.word2
    })

    detectWord.foreach(w => {
      if (sd.contains(w)) {
        println(w)
      }
    })

    time = System.currentTimeMillis - time
    println(time + " ms")
  }


}
