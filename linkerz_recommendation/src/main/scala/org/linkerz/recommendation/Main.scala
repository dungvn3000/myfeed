package org.linkerz.recommendation

import breeze.text.tokenize.JavaWordTokenizer
import breeze.text.analyze.CaseFolder
import breeze.text.transform.StopWordFilter
import org.linkerz.model.{Link, LinkDao}
import com.mongodb.casbah.commons.MongoDBObject
import collection.mutable.ListBuffer
import org.bson.types.ObjectId
import collection.immutable.HashSet
import org.apache.commons.math3.stat.Frequency
import org.apache.commons.math3.stat.correlation.{SpearmansCorrelation, PearsonsCorrelation}

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 11/8/12 1:49 AM
 *
 */
object Main extends App {


  val links = LinkDao.find(MongoDBObject.empty).filter(_.featureImage.size > 0).toList

  var time = System.currentTimeMillis()

  val rowName = new ListBuffer[ObjectId]
  var colName = new HashSet[String]
  val data = new ListBuffer[List[Double]]
  val frequencies = new ListBuffer[Frequency]

  val tokenizer = JavaWordTokenizer ~> StopWordFilter("vi")

  links.foreach(link => {
    rowName += link._id
    val f = new Frequency()
    tokenizer(CaseFolder(link.title + link.description)).foreach(word => {
      colName += word
      f.addValue(word)
    })
    f.valuesIterator()
    frequencies += f
  })

  for (i <- 0 to links.size - 1) {
    val d = colName.toList.map(name => {
      frequencies(i).getCount(name).toDouble
    })
    data += d
  }

  val score = new ListBuffer[(Link, Double)]

  val p1 = new SpearmansCorrelation()
  val p2 = new PearsonsCorrelation()

  for (i <- 0 to links.size - 1) {
    val r1 = p1.correlation(data(24).toArray, data(i).toArray)
    val r2 = p2.correlation(data(24).toArray, data(i).toArray)
    score += links(i) -> (r1 + r2) / 2
  }

  time = System.currentTimeMillis() - time

  score.sortWith(_._2 > _._2).take(10).foreach(s => s match {
    case (link,s) => println(link.title + " " + link.url + " " + " " + s)
  })

  println("time = " + time)
}
