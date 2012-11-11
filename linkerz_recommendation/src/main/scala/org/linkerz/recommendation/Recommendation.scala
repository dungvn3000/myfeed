package org.linkerz.recommendation

import org.linkerz.model.Link
import redis.clients.jedis.{Pipeline, Jedis}
import Correlation._
import collection.mutable.ListBuffer

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

  def buildScoreTable(userLinks: List[Link], newestLinks: List[Link], n: Int = 10, minScore:Double = 0.0) = {
    val scores = new ListBuffer[(String, String, Double)]
    userLinks.foreach(userLink => {
      val text1 = userLink.title + " " + userLink.description
      newestLinks.foreach(newestLink => {
        val text2 = newestLink.title + " " + newestLink.description
        scores += Tuple3(userLink.id, newestLink.id, sim_pearson(text1, text2))
      })
    })
    scores.sortWith(_._3 > _._3).filter(_._3 > minScore).take(n)
  }

}
