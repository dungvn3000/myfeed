/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
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

@Import(stylesheet = Array(
  "context:assets/css/main.css",
  "context:assets/css/subnav.css",
  "context:assets/bootstrap/css/bootstrap.css"
), library = Array(
  "context:assets/bootstrap/js/bootstrap.js"
))
class Layout {

  @Property
  val title = "LinkerZ"

}
