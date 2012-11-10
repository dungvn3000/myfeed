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

  stopWatch("Buil Score Table") {
    Recommendation ! links.drop(200)
  }

  stopWatch("Update a link to score table") {
    Recommendation <~ links.take(200)
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
    val m = redis.hgetAll("score:" + links(1).id)
    info(links(1).title)
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

  stopWatch("Get Recommnendation") {
    val m = redis.hgetAll("score:" + links(200).id)
    info(links(200).title + " " + links(200).url)
    m.foreach(t => {
      val link = LinkDao.findOneById(new ObjectId(t._1))
      info(link.get.url + " " + t._2)
    })
  }

  stopWatch("Get best match") {
    var bestMatch = (-1.0, "", "")
    links.foreach(link => {
      val map = redis.hgetAll("score:" + link.id)

      map.foreach(m => m match {
        case (key, value) => if (value.toDouble > bestMatch._1) {
          bestMatch = (value.toDouble, link.id, key)
        }
      })

    })

    val link1 = LinkDao.findOneById(new ObjectId(bestMatch._2))
    val link2 = LinkDao.findOneById(new ObjectId(bestMatch._3))

    info(link1.get.url)
    info(link2.get.url)
    info("bestMatch = " + bestMatch._1)
  }

}
