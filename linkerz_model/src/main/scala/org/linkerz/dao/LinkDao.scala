package org.linkerz.dao

import MongoTemplate._
import org.springframework.data.mongodb.core.query.Query._
import org.springframework.data.mongodb.core.query.Criteria._
import org.linkerz.model.Link

/**
 * The Class LinkDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/17/12 7:47 AM
 *
 */
class LinkDao {

  def findByUrl(url: String) = mongo.find(query(where("url").is(url)), classOf[Link])

  def save(link: Link): Boolean = {
    if (findByUrl(link.url).isEmpty) {
      mongo.save(link)
      return true
    }
    false
  }

}

object LinkDao {

  val _dao = new LinkDao

  def save(link: Link) = _dao.save(link)

  def findByUrl(url: String) = _dao.findByUrl(url)

}