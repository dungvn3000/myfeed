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
import collection.immutable.HashMap

/**
 * The Class WordExtractor.
 *
 * @author Nguyen Duc Dung
 * @since 8/8/12, 2:12 PM
 *
 */

object WordExtractor {

  val maximumLength = 9

  def extract(doc: TextDocument): List[(String, String)] = {
    val word = new ListBuffer[(String, String)]
    doc.getTextBlocks.foreach(block => {
      val result = extract(block.getText)
      if (!result.isEmpty) {
        word ++= result
      }
    })

    //Remove the duplicate in the same page. @Todo: testing this.
    removeDuplicate(word.toList)
  }

  def extract(string: String): List[(String, String)] = {
    val content = UnicodeTokenizer.tokenize(WordClean.clean(string).toLowerCase)

    //If the content is only one word return the word.
    if (content.size == 1 && content(0).length > 1
      && !NumberUtils.isNumber(content(0))
      && content(0).length <= maximumLength) return List((content(0), null))

    val word = new ListBuffer[(String, String)]
    var index = 0
    while (content.length > index + 1) {
      val st1 = content(index)
      val st2 = content(index + 1)
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

  def removeDuplicate(word: List[(String, String)]): List[(String, String)] = {
    var set = new mutable.HashSet[(String, String)]
    word.foreach(tuple2 => {
      set += tuple2
    })
    set.toList
  }

}
