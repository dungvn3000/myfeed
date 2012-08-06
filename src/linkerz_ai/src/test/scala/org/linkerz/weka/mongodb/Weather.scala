/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.weka.mongodb

import org.linkerz.weka.mongodb.annotation.Attribute

/**
 * The Class Weather.
 *
 * @author Nguyen Duc Dung
 * @since 8/7/12, 3:33 AM
 *
 */

class Weather {

  @Attribute(name = "outlook", values = Array("sunny", "overcast", "rainy"))
  var outlook: String = _

  @Attribute(name = "temperature")
  var temperature: Double = _

  @Attribute(name = "humidity")
  var humidity: Double = _

  @Attribute(name = "windy", values = Array("true", "false"))
  var windy: String = _

  @Attribute(name = "play", values = Array("yes", "no"))
  var play: String = _

}
