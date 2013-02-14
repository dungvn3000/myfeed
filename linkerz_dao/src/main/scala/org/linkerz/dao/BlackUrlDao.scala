package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.Imports._
import org.linkerz.model.BlackUrl

/**
 * The Class BlackUrlDao.
 *
 * @author Nguyen Duc Dung
 * @since 2/12/13 2:23 PM
 *
 */
object BlackUrlDao extends SalatDAO[BlackUrl, ObjectId](mongo("blackurl")) {

  def all = find(MongoDBObject.empty).toList

}
