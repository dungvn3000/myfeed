package org.linkerz.recommendation

import org.linkerz.model.Link
import redis.clients.jedis.{Pipeline, Jedis}
import org.apache.commons.math3.stat.Frequency
import breeze.text.tokenize.JavaWordTokenizer
import breeze.text.transform.StopWordFilter
import breeze.text.analyze.CaseFolder
import collection.JavaConversions._

/**
 * The Class DataSet.
 *
 * @author Nguyen Duc Dung
 * @since 11/8/12 12:53 PM
 *
 */
object DataSet {

  val redis = new Jedis("localhost")
  val dataSetPrefix = "dataset:"

  private def update(link: Link)(implicit pipelined: Pipeline) {
    //Step 1: Tokenize
    val tokenizer = JavaWordTokenizer ~> StopWordFilter("vi")
    val words = tokenizer(CaseFolder(link.title + link.description)).filter(word => word.trim.length > 1)

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

  def updateDataSet(link: Link) {
    inPipelined {
      implicit pipelined => {
        update(link)
      }
    }
  }

  def updateDataSet(links: List[Link]) {
    inPipelined {
      implicit pipelined =>
        links.foreach(link => update(link))
    }
  }

  private def inPipelined(f: Pipeline => Unit) {
    val pipelined = redis.pipelined()
    f(pipelined)
    pipelined.sync()
  }

}
