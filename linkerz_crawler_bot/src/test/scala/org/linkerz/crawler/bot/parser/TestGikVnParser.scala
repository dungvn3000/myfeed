package org.linkerz.crawler.bot.parser

/**
 * The Class TestGikVnParser.
 *
 * @author Nguyen Duc Dung
 * @since 11/13/12 9:09 PM
 *
 */
class TestGikVnParser extends NewsParserTest[GikVnParser] {

  withParser(new GikVnParser)

  test("Test 1 GikVnParser") {
    withUrl("http://gik.vn/1239-c60d81f35d-Chien-dich-Facebook-hieu-qua-voi-nhung-dong-xu.html")
    withTitle("Chiến dịch Facebook hiệu quả với những đồng xu")
    withImgUrl("http://www.jeffbullas.com/wp-content/uploads/2011/03/10-Best-Facebook-Campaigns-Jack-in-the-box-4.png")
    mustContains("Hughes đã được lựa chọn ngẫu nhiên trong số 283,000 fan và chiến thắng giải thưởng đó")
  }

  test("Test 2 GikVnParser") {
    withUrl("http://gik.vn/1232-d6a9450dc0-StartMeUp-thang-10-Cau-chuyen-khoi-nghiep.html")
    withTitle("StartMeUp tháng 10 - Câu chuyện khởi nghiệp")
    withImgUrl("http://gik.vn/upload/1_resize.jpg")
    mustContains("Một số bài học hay có thể rút ra sau cuộc trò chuyện với anh đó là:")
  }

  test("Test 3 GikVnParser") {
    withUrl("http://gik.vn/1223-297beb01dd-%5BStart-up%5D-Fab:-Buoc-ngoat-lon-va-9-trieu-nguoi-dung.html")
    withTitle("[Start-up] Fab: Bước ngoặt lớn và 9 triệu người dùng")
    withImgUrl("http://www.coolhunting.com/2011/06/09/fab-dot-come.jpg")
    mustContains("Fab khác biệt với những trang thương mại điện tử khác bởi nó không phải là nơi bán hàng thanh lý.")
  }

}
