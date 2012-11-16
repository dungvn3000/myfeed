package org.linkerz.model

import org.springframework.data.annotation.Id


/**
 * The Class User.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:05 PM
 *
 */

class User {
  @Id
  var id: String = _
  var userName: String = _
  var passWord: String = _
}