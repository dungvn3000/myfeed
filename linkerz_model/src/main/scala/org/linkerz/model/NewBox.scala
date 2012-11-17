package org.linkerz.model

import org.bson.types.ObjectId
import java.util.Date
import org.springframework.data.annotation.Id

/**
 * The Class NewBox.
 *
 * @author Nguyen Duc Dung
 * @since 11/11/12 2:25 AM
 *
 */
class NewBox {
  @Id
  var id: String = _
  var userId: String = _
  var linkId: String = _
  var click: Boolean = false
  var createDate: Date = new Date()
}