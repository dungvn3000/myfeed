package org.linkerz.dao

import org.springframework.data.mongodb.core
import com.mongodb.Mongo

/**
 * The Class MongTemplate.
 *
 * @author Nguyen Duc Dung
 * @since 11/16/12 9:18 PM
 *
 */
object MongoTemplate {

  val mongo = new core.MongoTemplate(new Mongo("localhost"), "linkerz")

}
