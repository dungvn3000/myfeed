package org.linkerz.recommendation

import org.linkerz.model.Link
import redis.clients.jedis.{Pipeline, Jedis}
import Correlation._
import collection.mutable.ListBuffer
import collection.JavaConversions._

/**
 * This object is using for make a recommendation to user.
 *
 * @author Nguyen Duc Dung
 * @since 11/8/12 12:53 PM
 *
 */
object Recommendation {

  val redis = new Jedis("localhost")
  val dataSetPrefix = "dataset:"
  val scorePrefix = "score:"

  private def inPipelined(f: Pipeline => Unit) {
    val pipelined = redis.pipelined()
    f(pipelined)
    pipelined.sync()
  }

  def ++(links: List[Link]) {
    inPipelined {
      implicit pipelined => {
        updateScore(links)
      }
    }
  }

  private def updateScore(links: List[Link])(implicit pipelined: Pipeline) {
    links.foreach(link1 => {
      val scores = new ListBuffer[(String, Double)]
      links.foreach(link2 => {
        if (link1 != link2) scores += link2.id -> sim_pearson(link1, link2)
      })

      pipelined.del(scorePrefix + link1.id)

      scores.toList.sortWith(_._2 > _._2).take(5).foreach(s => s match {
        case (id, score) => {
          pipelined.hmset(scorePrefix + link1.id, Map(id -> score.toString))
        }
      })
    })
  }


}
