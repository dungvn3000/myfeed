package org.linkerz.recommendation

import org.linkerz.model.Link
import org.linkerz.core.time.StopWatch
import collection.JavaConversions._
import org.linkerz.dao.MongoTemplate
import MongoTemplate._

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 11/8/12 1:49 AM
 *
 */
object Main extends App with StopWatch {

  val links = mongo.findAll(classOf[Link]).filter(_.featureImage.size > 0).toList

  //Assumption the user click on 10 links
  val userClickLinks = links.take(10)

  //And we got 250 news from the robot.
  val newestLink = links.takeRight(250)

  stopWatch("Build Score Table") {
    val scores = Recommendation.buildScoreTable(userClickLinks, newestLink, 3)
    scores.foreach(s => s match {
      case (id1, id2, score) => {
        val link1 = mongo.findById(id1, classOf[Link])
        val link2 = mongo.findById(id2, classOf[Link])

        info(link1.title + " - " + link2.title + " - " + score)
      }
    })
  }


}
