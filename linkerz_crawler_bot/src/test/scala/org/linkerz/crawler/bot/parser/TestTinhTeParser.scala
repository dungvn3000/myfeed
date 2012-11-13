package org.linkerz.crawler.bot.parser

import core.{ParserData, NewsParser}

/**
 * The Class TestTinhTeParser.
 *
 * @author Nguyen Duc Dung
 * @since 11/13/12 11:30 PM
 *
 */
class TestTinhTeParser extends NewsParserTest[TinhTeParser] {

  withParser(new TinhTeParser)

  test("Test 1 TinhTe.vn") {
    withUrl("http://www.tinhte.vn/threads/1659411/")
    withTitle("[The Big Picture] Venice ngập trong nước")
    withImgUrl("http://cdn.tinhte.vn/attachments/784339/")
    mustContains("Một người đàn ông và một phụ nữ bơi trong dòng nước lụt ở Piazza San Marco (quảng trường Thánh Mark) tại Venice, Italy, 11/11/2012. Triều cường đã làm ngập lụt Venice, buộc người dân Venice và du khách phải mang ủng cao hoặc dùng các lối đi bằng gỗ để đi lại ở quảng trường Piazza San Marco cũng như các nơi bị ngập khác.")
  }

  test("Test 2 TinhTe.vn") {
    withUrl("http://www.tinhte.vn/threads/1654007/")
    withTitle("[The Big Picture] Đời thường 10/2012")
    withImgUrl("http://cdn.tinhte.vn/attachments/782332/")
    mustContains("Trăng tròn phía sau một bức tượng con bò đực nhìn về khu vực chăn nuôi gia súc cũ ở Kansas, Missouri, 29/10/2012.")
  }

  test("Test 3 TinhTe.vn") {
    withUrl("http://www.tinhte.vn/threads/1659362/")
    withTitle("Những mẫu máy tính bảng mới dùng Windows 8 (Phần 2)")
    withImgUrl("http://cdn.tinhte.vn/attachments/785549/")
    mustContains("ElitePad - cái tên mang tiền tố Elite của dòng máy tính EliteBook nổi tiếng nên có thể dễ dàng đoán ra đây là một sản phẩm dành cho thị trường doanh nghiệp")
  }

}
