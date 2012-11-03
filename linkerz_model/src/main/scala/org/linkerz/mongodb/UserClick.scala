/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.mongodb

/**
 * This class using for store link user clicked on it.
 *
 * @author Nguyen Duc Dung
 * @since 9/5/12 3:21 PM
 *
 */
class UserClick extends LinkerZEntity {
  var userName: String = _
  var linkId: String = _
  var title: String = _
  var clicked: Boolean = _
}
