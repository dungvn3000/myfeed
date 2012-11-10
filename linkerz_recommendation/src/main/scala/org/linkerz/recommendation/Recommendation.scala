package org.linkerz.recommendation

import org.linkerz.model.{LinkDao, Link}
import redis.clients.jedis.{Pipeline, Jedis}
import Correlation._
import collection.mutable.ListBuffer
import collection.JavaConversions._
import org.bson.types.ObjectId
import collection.immutable.HashMap

/**
 * This object is using for make a recommendation to user.
 *
 * @author Nguyen Duc Dung
 * @since 11/8/12 12:53 PM
 *
 */
object Recommendation {

  val redis = new Jedis("localhost")
  val scorePrefix = "score:"
  val linkPrefix = "linkText"

  private def inPipelined(f: Pipeline => Unit) {
    val pipelined = redis.pipelined()
    f(pipelined)
    pipelined.sync()
  }

  def buildScoreTable(userLinks: List[Link], newestLinks: List[Link]) = {
    val scores = new ListBuffer[(String, String, Double)]
    userLinks.foreach(userLink => {
      val text1 = userLink.title + " " + userLink.description
      newestLinks.foreach(newestLink => {
        val text2 = newestLink.title + " " + newestLink.description
        scores += Tuple3(userLink.id, newestLink.id, sim_pearson(text1, text2))
      })
    })
    scores.sortWith(_._3 > _._3).take(10)
  }

}
