package org.linkerz.model

import java.util.Date
import org.springframework.data.annotation.Id

/**
 * The Class Error.
 *
 * @author Nguyen Duc Dung
 * @since 11/13/12 12:39 AM
 *
 */
class Error {
  @Id
  var id: String = _
  var name: String = _
  var message: String = _
  var className: String = _
  var url: String = _
  var createDate: Date = new Date
}
