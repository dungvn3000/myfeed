/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.components

import org.apache.tapestry5.annotations.{Import, Property}

/**
 * The Class Layout.
 *
 * @author Nguyen Duc Dung
 * @since 8/2/12, 9:24 PM
 *
 */
@Import(stylesheet = Array("context:assets/css/bootstrap.css", "context:assets/css/main.css"))
class Layout {

  @Property
  val title = "LinkerZ Search and Sharing"

}
