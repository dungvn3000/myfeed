package vn.myfeed.dao

import org.scalatest.FunSuite
import org.joda.time.DateTime

/**
 * The Class LinkDaoSuite.
 *
 * @author Nguyen Duc Dung
 * @since 2/4/13 11:59 PM
 *
 */
class LinkDaoSuite extends FunSuite {

  test("get last news in 7 days") {
    val last7Day = DateTime.now.minusDays(7)
    val links = NewsDao.getAfter(last7Day)

    assert(!links.isEmpty)
    assert(links.last.createdDate.isAfter(last7Day))
  }

}
