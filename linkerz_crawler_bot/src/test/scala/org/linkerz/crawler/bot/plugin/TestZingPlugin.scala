/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin

import org.linkerz.crawler.core.factory.DefaultDownloadFactory
import org.linkerz.crawler.core.job.CrawlJob
import org.junit.{Assert, Test}
import org.linkerz.crawler.bot.parser.ZingParser

/**
 * The Class TestZingPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/21/12, 3:14 AM
 *
 */
class TestZingPlugin extends NewsParserTest[ZingParser] {

  withParser(new ZingParser)

  test("Test 1 ZingParser") {
    withUrl("http://news.zing.vn/doi-song/benh-nhan-an-com-tu-thien-nha-chua-tien-dua-cho-bac-sy/a284862.html")
    withTitle("'Bệnh nhân ăn cơm từ thiện nhà chùa, tiền đưa cho bác sỹ'")
    withImgUrl("http://img2.news.zing.vn/2012/11/13/mstien1.jpg")
    mustContains("Chủ tịch QH Nguyễn Sinh Hùng đã cho Bộ trưởng Y tế \"nợ\" câu trả lời về y đức sang buổi chất vấn sáng 14/11. Vấn đề tăng viện phí, chênh lệch giá thuốc đấu thầu đã được Bộ trưởng Nguyễn Thị Kim Tiến giải trình nhưng chưa làm các đại biểu hài lòng, đến cuối buổi có nhiều đại biểu vẫn chất vấn lại.")
  }

  test("Test 2 ZingParser") {
    withUrl("http://news.zing.vn/xa-hoi/bat-duoc-chim-la-sai-canh-1m-o-dong-thap/a284381.html")
    withTitle("Bắt được chim lạ, sải cánh 1m ở Đồng Tháp")
    withImgUrl("http://img2.news.zing.vn/2012/11/11/images1048857chimlaodongthapkienthucnetvn1.jpg")
    mustContains("Vào khoảng 20h ngày 9/11, anh Bình đã bắn bị thương và bắt được một con chim lạ có hình dáng và bộ mặt rất giống với chim ưng, sải cánh khoảng 1m, móng vuốt và mỏ rất sắc bén. Trong khi bắt, anh đã bị chân nó quắp đến chảy máu tay")
  }

  test("Test 3 ZingParser") {
    withUrl("http://news.zing.vn/doi-song/con-trai-kien-me-doi-tien-phung-duong/a284392.html")
    withTitle("Con trai kiện mẹ... đòi tiền phụng dưỡng ")
    withImgUrl("http://img2.news.zing.vn/2012/11/11/1-24.jpg")
    mustContains("Sinh con ra với mong muốn con mình trưởng thành, có thể nương tựa khi về già nhưng với cụ Nguyễn Thị Th. (SN 1922 ở Tam Đảo, Vĩnh Phúc), điều đó lại ngược lại. Người con trai cả mà cụ Th. dứt ruột đẻ ra đã đòi công nuôi dưỡng trong những ngày mẹ ở nhà mình. Điều đó khiến cho cụ Th. đắng lòng khóc không ra nước mắt")
  }


}
