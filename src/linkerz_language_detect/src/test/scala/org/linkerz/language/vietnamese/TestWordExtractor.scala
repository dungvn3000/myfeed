/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.language.vietnamese

import org.scalatest.FunSuite
import org.linkerz.test.spring.SpringContext

import org.linkerz.language.detect.vietnamese.WordExtractor
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.{WordTuple2, Link}
import java.io.ByteArrayInputStream
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput
import org.xml.sax.InputSource
import collection.JavaConversions._
import collection.JavaConverters._
import org.springframework.data.mongodb.core.query.{Criteria, Query}
import collection.mutable.ListBuffer
import org.linkerz.test.categories.ManualTest
import org.junit.experimental.categories.Category


/**
 * The Class TestWordExtrator.
 *
 * @author Nguyen Duc Dung
 * @since 8/8/12, 2:28 PM
 *
 */
@Category(Array(classOf[ManualTest]))
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
    var result: List[WordTuple2] = Nil
    links.foreach(link => {
      val inputStream = new ByteArrayInputStream(link.content)
      val doc = new BoilerpipeSAXInput(new InputSource(inputStream)).getTextDocument
      result ++= WordExtractor.extract(doc, link.url)
    })

    time = System.currentTimeMillis - time

    println("Parse Time:")
    println(links.size + " pages")
    println(result.size + " results")
    println(time + " ms")

    time = System.currentTimeMillis
    result.foreach(tuple2 => {
      val word1 = tuple2.word1
      val word2 = tuple2.word2

      if (word1 != null && word2 != null) {
        var wordTuple2 = mongoOperations.findOne(
          Query.query(Criteria.where("word1").is(word1).and("word2").is(word2)),
          classOf[WordTuple2])
        if (wordTuple2 == null) {
          wordTuple2 = new WordTuple2(word1, word2)
        }
        wordTuple2.count += 1
        if (!wordTuple2.urls.contains(tuple2.urls.head)) {
          wordTuple2.urls ++= tuple2.urls
          wordTuple2.page += 1
        }
        mongoOperations.save(wordTuple2)
      }
    })

    //Remove the low quality word
    val wordTuple2s = mongoOperations.findAll(classOf[WordTuple2])
    val deleteWord = new ListBuffer[WordTuple2]

    wordTuple2s.foreach(wordTuple2 => {
      if (wordTuple2.page == wordTuple2.count || wordTuple2.page == 1) {
        deleteWord += wordTuple2
      }
    })

    deleteWord.foreach(tuple => {
      mongoOperations.remove(tuple)
    })


    time = System.currentTimeMillis - time

    println("Stored time:")
    println(time + " ms")
  }

  test("testRemoveDuplicate") {
//    var word = List(("dung", "dung"), ("dung", "dung"), ("dung", "ne"))
//    word = WordExtractor.removeDuplicate(word)
//    assert(word.size == 2)
  }
}
