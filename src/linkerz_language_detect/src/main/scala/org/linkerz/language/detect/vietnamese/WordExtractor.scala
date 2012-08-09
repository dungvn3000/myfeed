/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.language.detect.vietnamese

import de.l3s.boilerpipe.document.TextDocument
import collection.mutable.ListBuffer

import collection.JavaConversions._
import de.l3s.boilerpipe.util.UnicodeTokenizer
import org.apache.commons.lang.math.NumberUtils
import collection.mutable
import org.linkerz.mongodb.model.WordTuple2

/**
 * The Class WordExtractor.
 *
 * @author Nguyen Duc Dung
 * @since 8/8/12, 2:12 PM
 *
 */

object WordExtractor {

  val maximumLength = 9

  /**
   * Minimum value is 1
   */
  val minimumLengthForSentence = 5

  def extract(doc: TextDocument, url: String): List[WordTuple2] = {
    val word = new ListBuffer[WordTuple2]
    doc.getTextBlocks.foreach(block => {
      val result = extract(block.getText)
      result.foreach(tuple2 => {
        val wordTuple2 = new WordTuple2(tuple2._1, tuple2._2)
        wordTuple2.urls += url
        word += wordTuple2
      })
    })

    //Remove the duplicate in the same page. @Todo: testing this.
    //    removeDuplicate(word.toList)

    removeLowWord(word.toList)
  }

  def extract(string: String): List[(String, String)] = {
    val content = UnicodeTokenizer.tokenize(WordClean.clean(string).toLowerCase)

    //If the sentence is too short, then skip it.
    if (content.size < minimumLengthForSentence) return Nil

    val word = new ListBuffer[(String, String)]
    var index = 0
    while (content.length > index + 1) {
      val st1 = content(index).trim
      val st2 = content(index + 1).trim
      if (!NumberUtils.isNumber(st1) || !NumberUtils.isNumber(st2)) {
        if (!StopWordFilter.isStopWord(st1) || !StopWordFilter.isStopWord(st2)) {
          if (st1.length <= maximumLength && st2.length <= maximumLength) {
            //The pair of word, both of them are not number and stop word.
            word += new Tuple2[String, String](st1, st2)
          }
        }
      }
      index += 1
    }

    word.toList
  }

  def removeLowWord(word: List[WordTuple2]): List[WordTuple2] = {
    var map = new mutable.HashMap[WordTuple2, Int]()
    word.foreach(tuple2 => {
      if (!map.get(tuple2).isEmpty) {
        val count = map.get(tuple2).get + 1
        map.put(tuple2, count)
      } else {
        map.put(tuple2, 1)
      }
    })

    val removeWord = new ListBuffer[WordTuple2]
    map.foreach(tuple => {
      if (tuple._2 == 1) {
        removeWord += tuple._1
      }
    })

    word.filter(wordTuple2 => !removeWord.contains(wordTuple2))
  }

  def removeDuplicate(word: List[WordTuple2]): List[WordTuple2] = {
    var set = new mutable.HashSet[WordTuple2]
    word.foreach(tuple2 => {
      set += tuple2
    })
    set.toList
  }

}
