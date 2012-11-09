package org.linkerz.recommendation

import org.linkerz.model.LinkDao
import com.mongodb.casbah.commons.MongoDBObject
import org.linkerz.core.time.StopWatch

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 11/8/12 1:49 AM
 *
 */
object Main extends App with StopWatch {

  val links = LinkDao.find(MongoDBObject.empty).filter(_.featureImage.size > 0).toList

  stopWatch("Build DataSet with " + links.size + " links") {
    DataSet.updateDataSet(links)
  }

}
