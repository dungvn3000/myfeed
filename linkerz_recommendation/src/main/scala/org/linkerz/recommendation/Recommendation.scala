package org.linkerz.recommendation

import org.linkerz.model.{LinkDao, Link}
import redis.clients.jedis.{Pipeline, Jedis}
import Correlation._
import collection.mutable.ListBuffer
import collection.JavaConversions._
import org.bson.types.ObjectId

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

  def !(links: List[Link]) {
    //Clean score table
    val keys = redis.keys(scorePrefix + "*")
    keys.foreach(redis.del(_))
    redis.del(linkPrefix)

    inPipelined {
      implicit pipelined => {
        buildScoreTable(links)
      }
    }
  }

  def <~(link1: Link) {
    val keys = redis.keys(scorePrefix + "*")
    val text = redis.hgetAll(linkPrefix)
    inPipelined {
      implicit pipelined => {
        updateScoreTable(link1, keys, text)
      }
    }
  }

  def <~(links: List[Link]) {
    val keys = redis.keys(scorePrefix + "*")
    val text = redis.hgetAll(linkPrefix)
    inPipelined {
      implicit pipelined => {
        links.foreach(link => {
          updateScoreTable(link, keys, text)
        })
      }
    }
  }

  def ?(link: Link) = {
    val scores = redis.hgetAll(scorePrefix + link.id).toList
    scores.sortWith(_._2.toDouble > _._2.toDouble).take(5)
  }

  private def updateScoreTable(link1: Link, keys: java.util.Set[String], text: java.util.Map[String, String])(implicit pipelined: Pipeline) {
    val scores = new ListBuffer[(String, Double)]
    val text1 = link1.title + " " + link1.description
    keys.foreach(key => {
      val link2Id = key.split(":")(1)
      if (link2Id != link1.id) {
        val text2 = text.find(_._1 == link2Id)
        if (text2.isEmpty) println("link2Id = " + link2Id)
        scores += link2Id -> sim_pearson(text1, text2.get._2)
      }
    })

    //Store text for faster accessing
    text += link1.id -> text1
    pipelined.hmset(linkPrefix, Map(link1.id -> text1))

    pipelined.del(scorePrefix + link1.id)

    scores.toList.sortWith(_._2 > _._2).take(5).foreach(s => s match {
      case (id, score) => {
        pipelined.hmset(scorePrefix + link1.id, Map(id -> score.toString))
        pipelined.hmset(scorePrefix + id, Map(link1.id -> score.toString))
      }
    })

    //Store the key for faster accessing
    keys += scorePrefix + link1.id
  }


  private def buildScoreTable(links: List[Link])(implicit pipelined: Pipeline) {
    links.foreach(link1 => {
      val scores = new ListBuffer[(String, Double)]
      links.foreach(link2 => {
        if (link1 != link2) {
          val text1 = link1.title + " " + link1.description
          val text2 = link2.title + " " + link2.description
          scores += link2.id -> sim_pearson(text1, text2)

          //Store link information
          pipelined.hmset(linkPrefix, Map(link1.id -> text1))
          pipelined.hmset(linkPrefix, Map(link2.id -> text2))
        }
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
