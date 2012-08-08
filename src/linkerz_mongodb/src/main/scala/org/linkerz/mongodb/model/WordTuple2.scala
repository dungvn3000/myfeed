/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.mongodb.model

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

  def this(word1: String, word2: String) {
    this()
    this.word1 = word1
    this.word2 = word2
  }

}
