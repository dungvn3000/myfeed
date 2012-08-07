/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.mongodb.model

/**
 * The Class LinkConnection.
 *
 * @author Nguyen Duc Dung
 * @since 8/7/12, 10:17 PM
 *
 */

class LinkConnection extends LinkerZEntity {

  var formId: String = _

  var toId: String = _

  def this(formId: String, toId: String) {
    this()
    this.formId = formId
    this.toId = toId
  }
}
