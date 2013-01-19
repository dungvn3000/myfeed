package org.linkerz.model

import java.util.Date

/**
 * The Class NewsBox.
 *
 * @author Nguyen Duc Dung
 * @since 11/11/12 2:25 AM
 *
 */
case class NewsBox
(
  id: String,
  userId: String,
  linkId: String,
  groupName: String,
  click: Boolean = false,
  createdDate: Date = new Date()
  )