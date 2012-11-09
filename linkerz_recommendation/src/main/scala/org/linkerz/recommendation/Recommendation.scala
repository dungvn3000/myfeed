package org.linkerz.recommendation

import org.linkerz.model.Link
import redis.clients.jedis.{Pipeline, Jedis}
import org.apache.commons.math3.stat.Frequency
import breeze.text.tokenize.JavaWordTokenizer
import breeze.text.transform.StopWordFilter
import breeze.text.analyze.CaseFolder
import collection.JavaConversions._
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
  val dataSetPrefix = "dataset:"
  val scorePrefix = "score:"

  private def updateDataSet(link: Link)(implicit pipelined: Pipeline) {
    //Step 1: Tokenize
    val tokenizer = JavaWordTokenizer ~> StopWordFilter("vi")
    val words = tokenizer(CaseFolder(link.title + " " + link.description)).filter(word => word.trim.length > 1)

    //Step 2: Counting.
    val frequency = new Frequency()
    words.foreach(word => {
      frequency.addValue(word)
    })

    //Step 3: Store data to redis.
    frequency.valuesIterator().foreach(word => {
      pipelined.hmset(dataSetPrefix + link.id, Map(word.toString -> frequency.getCount(word.toString).toString))
    })
  }

  private def updateScore() {
    val keys = redis.keys(dataSetPrefix + "*")
    keys.foreach(key1 => {
      val id1 = key1.split(":")(1)
      val data1 = redis.hgetAll(key1)
      val scores = new ListBuffer[(String, String)]
      keys.foreach(key2 => {
        if (key1 != key2) {
          val id2 = key2.split(":")(1)
          val data2 = redis.hgetAll(key2)
          val score = sim_pearson(data1.toMap, data2.toMap)
          scores +=  id2 -> score.toString
        }
      })

      redis.del(scorePrefix + id1)

      scores.sortWith(_._2 < _._2).take(5).foreach(score => {
        redis.hmset(scorePrefix + id1, Map(score._1 -> score._2))
      })
    })
  }

  def +=(link: Link) {
    inPipelined {
      implicit pipelined => {
        updateDataSet(link)
      }
    }
  }

  def ++=(links: List[Link]) {
    inPipelined {
      implicit pipelined =>
        links.foreach(link => updateDataSet(link))
    }
    updateScore()
  }

  private def inPipelined(f: Pipeline => Unit) {
    val pipelined = redis.pipelined()
    f(pipelined)
    pipelined.sync()
  }

}
