package org.linkerz.recommendation

import org.linkerz.model.LinkDao
import com.mongodb.casbah.commons.MongoDBObject
import org.linkerz.core.time.StopWatch
import redis.clients.jedis.Jedis
import collection.JavaConversions._
import org.bson.types.ObjectId

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
    Recommendation ++= links
  }

  val redis = new Jedis("localhost")

  stopWatch("Get Recommendation") {
    val m = redis.hgetAll("score:" + links(0).id)
    info(links(0).title)
    m.foreach(t => {
      val link = LinkDao.findOneById(new ObjectId(t._1))
      info(link.get.url + " " + t._2)
    })
  }

  stopWatch("Get Recommnendation") {
    val m = redis.hgetAll("score:" + links(44).id)
    info(links(44).title)
    m.foreach(t => {
      val link = LinkDao.findOneById(new ObjectId(t._1))
      info(link.get.url + " " + t._2)
    })
  }

  stopWatch("Get Recommnendation") {
    val m = redis.hgetAll("score:" + links(99).id)
    info(links(99).title + " " + links(99).url)
    m.foreach(t => {
      val link = LinkDao.findOneById(new ObjectId(t._1))
      info(link.get.url + " " + t._2)
    })
  }
}
