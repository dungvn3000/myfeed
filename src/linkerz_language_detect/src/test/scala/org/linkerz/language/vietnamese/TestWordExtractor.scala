/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.language.vietnamese

import org.scalatest.FunSuite
import org.linkerz.test.spring.SpringContext

import org.linkerz.language.detect.vietnamese.WordExtractor
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.{WordTuple, WordTuple2, Link}
import java.io.ByteArrayInputStream
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput
import org.xml.sax.InputSource
import collection.JavaConversions._
import org.springframework.data.mongodb.core.query.{Criteria, Query}


/**
 * The Class TestWordExtrator.
 *
 * @author Nguyen Duc Dung
 * @since 8/8/12, 2:28 PM
 *
 */

class TestWordExtractor extends FunSuite with SpringContext {

  test("testExtractor") {
    val st1 = "Lực sĩ Hàn trật khuỷu tay vì nâng tạ quá nặng 1854 03082012"
    val result1 = WordExtractor.extract(st1)
    assert(result1.length == 11)

    val st2 = "Michael Phelps đã đòi nợ sòng phẳng với Lochte bằng HCV 200m hỗn hợp tâm HCV thứ 16 trong tổng số 20 HC Olympic của anh"
    val result2 = WordExtractor.extract(st2)
    assert(result2.length == 24)
  }


  test("testPageExtractor") {
    val mongoOperations = context.getBean("mongoTemplate", classOf[MongoOperations])
    val links = mongoOperations.findAll(classOf[Link])
    assert(links.size > 0)

    var time = System.currentTimeMillis()
    var result: List[(String, String)] = Nil
    links.foreach(link => {
      val inputStream = new ByteArrayInputStream(link.content)
      val doc = new BoilerpipeSAXInput(new InputSource(inputStream)).getTextDocument
      result = WordExtractor.extract(doc)
    })

    time = System.currentTimeMillis - time

    println("Parse Time:")
    println(links.size + " pages")
    println(result.size + " results")
    println(time + " ms")

    time = System.currentTimeMillis
    result.foreach(tuple2 => {
      val word1 = tuple2._1
      val word2 = tuple2._2

      if (word1 != null && word2 != null) {
        var wordTuple2 = mongoOperations.findOne(
          Query.query(Criteria.where("word1").is(word1).and("word2").is(word2)),
          classOf[WordTuple2])
        if (wordTuple2 == null) {
          wordTuple2 = new WordTuple2(word1, word2)
          mongoOperations.save(wordTuple2)
        }
      } else {
        var word = ""
        if (word1 == null) word = word2
        if (word2 == null) word = word1
        var wordTuple = mongoOperations.findOne(Query.query(Criteria.where("word").is(word)),
          classOf[WordTuple])
        if (wordTuple == null) {
          wordTuple = new WordTuple(word)
          mongoOperations.save(wordTuple)
        }
      }
    })

    time = System.currentTimeMillis - time

    println("Stored time:")
    println(time + " ms")
  }
}
