package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.Imports._
import org.linkerz.model.Image

/**
 * The Class ImageDao.
 *
 * @author Nguyen Duc Dung
 * @since 3/29/13 8:07 PM
 *
 */
object ImageDao extends SalatDAO[Image, ObjectId](mongo("image")) {



}
