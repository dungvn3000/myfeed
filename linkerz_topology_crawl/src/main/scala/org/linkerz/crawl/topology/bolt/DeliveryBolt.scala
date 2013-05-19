package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation
import breeze.text.tokenize.{JavaWordTokenizer, Tokenizer}
import breeze.text.transform.StopWordFilter
import breeze.text.analyze.CaseFolder
import scala.collection.immutable.HashSet
import org.apache.commons.math3.stat.Frequency
import scala.collection.mutable.ListBuffer
import org.bson.types.ObjectId
import org.linkerz.crawl.topology.event.PersistentDone
import org.linkerz.dao.{UserFeedDao, UserDao, UserNewsDao}
import org.linkerz.model.UserNews

/**
 * The Class DeliveryBolt.
 *
 * @author Nguyen Duc Dung
 * @since 5/19/13 11:07 AM
 *
 */
class DeliveryBolt extends StormBolt(outputFields = List("userId", "event")) with Logging {

  @transient var pearsonsCorrelation: PearsonsCorrelation = _
  @transient var tokenizer: Tokenizer = _

  setup {
    pearsonsCorrelation = new PearsonsCorrelation
    tokenizer = JavaWordTokenizer ~> StopWordFilter("vi")
  }

  execute {
    tuple => tuple matchSeq {
      case Seq(feedId: ObjectId, PersistentDone(news)) => {
        UserDao.all.foreach(user => if (UserFeedDao.findFeed(user._id, feedId).isDefined) {

          val userClicks = UserNewsDao.getUserClicks(user._id)

          //I just find the best score, if the best score more then 0.5, i'll turn the flag recommend on.
          var bestScore = 0d

          news.text.map(text1 => {
            userClicks.foreach(click => click.text.map {
              text2 =>
                val score = sim_pearson(text1, text2)
                if (score > bestScore) bestScore = score
            })
          })

          val recommend = if (bestScore > 0.5) true else false

          val userNews = UserNews(
            userId = user._id,
            newsId = news._id,
            feedId = news.feedId,
            score = bestScore,
            recommend = recommend
          )

          UserNewsDao.save(userNews)
        })

        tuple.ack()
      }
    }
  }

  /**
   * Calculate similar score between two texts by using @see PearsonsCorrelation.
   * @param s1
   * @param s2
   * @return
   */
  def sim_pearson(s1: String, s2: String) = {
    //Step 1: Tokenize
    val words1 = tokenizer(CaseFolder(s1)).filter(word => word.trim.length > 1)
    val words2 = tokenizer(CaseFolder(s2)).filter(word => word.trim.length > 1)

    var keys = new HashSet[String]
    //Step 2: Counting.
    val frequency1 = new Frequency()
    words1.foreach(word => {
      frequency1.addValue(word)
      keys += word
    })

    val frequency2 = new Frequency()
    words2.foreach(word => {
      frequency2.addValue(word)
      keys += word
    })

    val data1 = new ListBuffer[Double]()
    val data2 = new ListBuffer[Double]()
    keys.foreach(word => {
      data1 += frequency1.getCount(word)
      data2 += frequency2.getCount(word)
    })

    pearsonsCorrelation.correlation(data1.toArray, data2.toArray)
  }

}
