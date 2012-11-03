/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model

/**
 * The Class Word.
 *
 * @author Nguyen Duc Dung
 * @since 8/8/12, 4:28 PM
 *
 */

class WordTuple extends LinkerZEntity {

  var word: String = _
  var count: Long = 0

  def this(word: String) {
    this()
    this.word = word
  }
}
