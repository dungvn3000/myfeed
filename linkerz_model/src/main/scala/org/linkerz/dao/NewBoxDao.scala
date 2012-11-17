package org.linkerz.dao

import org.linkerz.model.Link

/**
 * The Class NewBoxDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/17/12 8:23 AM
 *
 */
class NewBoxDao {

  def findByUserId(userId: String): List[Link] = {
    Nil
  }

  def getUserClicked(userId: String) = {
    Nil
  }


}

object NewBoxDao {

  val _dao = new NewBoxDao

  def findByUserId(userId: String): List[Link] = _dao.findByUserId(userId)

  def getUserClicked(userId: String) = _dao.getUserClicked(userId)

}