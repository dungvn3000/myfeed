package org.linkerz.model.mongodb

import org.springframework.data.mongodb.core.MongoTemplate
import com.mongodb.Mongo

/**
 * The Class MongoOperation.
 *
 * @author Nguyen Duc Dung
 * @since 11/4/12 9:04 AM
 *
 */
object MongoOperation {

  val mongo = new MongoTemplate(new Mongo("localhost"), "linkerz")

}
