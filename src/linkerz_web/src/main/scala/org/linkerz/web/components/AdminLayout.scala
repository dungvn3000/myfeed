/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.web.components

import org.apache.tapestry5.annotations.{Property, Import}

/**
 * The Class AdminLayout.
 *
 * @author Nguyen Duc Dung
 * @since 8/10/12, 3:42 AM
 *
 */

@Import(stylesheet = Array(
  "context:assets/css/main.css",
  "context:assets/css/subnav.css",
  "context:assets/bootstrap/css/bootstrap.css"
), library = Array(
  "context:assets/bootstrap/js/bootstrap-transition.js",
  "context:assets/bootstrap/js/bootstrap-alert.js",
  "context:assets/bootstrap/js/bootstrap-modal.js",
  "context:assets/bootstrap/js/bootstrap-dropdown.js",
  "context:assets/bootstrap/js/bootstrap-scrollspy.js",
  "context:assets/bootstrap/js/bootstrap-tab.js",
  "context:assets/bootstrap/js/bootstrap-tooltip.js",
  "context:assets/bootstrap/js/bootstrap-popover.js",
  "context:assets/bootstrap/js/bootstrap-button.js",
  "context:assets/bootstrap/js/bootstrap-collapse.js",
  "context:assets/bootstrap/js/bootstrap-carousel.js",
  "context:assets/bootstrap/js/bootstrap-typeahead.js"
))
class AdminLayout {

  @Property
  val title = "LinkerZ - Admin"

}
