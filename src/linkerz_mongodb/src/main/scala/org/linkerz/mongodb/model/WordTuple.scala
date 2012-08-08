/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.mongodb.model

/**
 * The Class Word.
 *
 * @author Nguyen Duc Dung
 * @since 8/8/12, 4:28 PM
 *
 */

class WordTuple extends LinkerZEntity {

  var word: String = _

  def this(word: String) {
    this()
    this.word = word
  }
}
