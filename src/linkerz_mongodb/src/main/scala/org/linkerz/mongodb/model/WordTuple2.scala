/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.mongodb.model

import java.util

/**
 * The Class WordTuple2.
 *
 * @author Nguyen Duc Dung
 * @since 8/8/12, 4:06 PM
 *
 */

class WordTuple2 extends LinkerZEntity {

  var word1: String = _
  var word2: String = _
  var count: Long = 0
  var page: Long = 0
  var urls: util.List[String] = new util.ArrayList[String]


  def this(word1: String, word2: String) {
    this()
    this.word1 = word1
    this.word2 = word2
  }

  override def equals(obj: Any) = {
    obj.isInstanceOf[WordTuple2] &&
      obj.asInstanceOf[WordTuple2].word1 == word1 &&
      obj.asInstanceOf[WordTuple2].word2 == word2
  }

  override def hashCode() = word1.hashCode & word2.hashCode
}
