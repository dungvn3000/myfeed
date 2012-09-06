/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.weka.mongodb

import org.linkerz.weka.mongodb.annotation.Attribute
import org.linkerz.weka.mongodb.annotation.Attribute.TYPE._

/**
 * The Class Weather.
 *
 * @author Nguyen Duc Dung
 * @since 8/7/12, 3:33 AM
 *
 */
class Weather {

  @Attribute(name = "outlook", attType = NOMINAL, values = Array("sunny", "overcast", "rainy"))
  var outlook: String = _

  @Attribute(name = "temperature")
  var temperature: Double = _

  @Attribute(name = "humidity")
  var humidity: Double = _

  @Attribute(name = "windy", attType = NOMINAL, values = Array("true", "false"))
  var windy: String = _

  @Attribute(name = "play", attType = NOMINAL, values = Array("yes", "no"))
  var play: String = _

}
