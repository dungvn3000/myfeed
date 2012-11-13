package org.linkerz.crawler.bot.plugin

import parser.ActionVnPlugin

/**
 * The Class TestActionVnPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 11/13/12 8:15 PM
 *
 */
class TestActionVnPlugin extends ParserPluginTest[ActionVnPlugin] {

  withParser(new ActionVnPlugin)

  test("Test 1 ActionVnPlugin") {
    withUrl("http://www.action.vn/khoi-nghiep/thong-tin-ho-tro-khoi-nghiep/2599-khoi-nghiep-hanh-trinh-trieu-do")
    withTitle("Khởi nghiệp – Hành trình triệu đô")
    withImgUrl("http://www.action.vn/images/stories/Content_update/121113/rao-can.jpg")
    mustContains("Văn hóa khởi nghiệp đã có từ rất lâu đời trên toàn thế giới, đặc biệt là ở các nước phương tây. Ở độ tuổi 16 họ đã bắt đầu sống tự lập và có thể tự thân khởi nghiệp từ 2 bàn tay trắng")
  }

  test("Test 2 ActionVnPlugin") {
    withUrl("http://www.action.vn/goc-doanh-nghiep/tin-doanh-nghiep/2600-lazada-nhan-them-dau-tu-40-trieu-usd-tu-kinnevilk")
    withTitle("Lazada nhận thêm đầu tư 40 triệu USD từ Kinnevilk")
    withImgUrl("http://www.action.vn/images/stories/Content_update/121113/519x192xlazada-indo.jpg.pagespeed.ic.NvG2sd1dhR.jpg")
    mustContains("Thật thú vị khi biết Lazada công khai số vốn đầu tư mà mình nhận được. Trước đó, trang thương mại điện tử clone mô hình Amazon này tỏ ra khá mơ hồ khi đề cập đến các con số thương mại. Giám đốc điều hành tại Indonesia của Lazada, ông Magnus Ekbom cho biết rằng họ không thể tiết lộ bất kỳ con số chi tiết nào vì một số lý d")
  }

  test("Test 3 ActionVnPlugin") {
    withUrl("http://www.action.vn/tin-cong-nghe/tin-moi/2594-the-startup-kids-the-gioi-cua-nhung-nha-sang-lap-vimeo-soundcloud-kiip-dropbox-foodspotting")
    withTitle("“The Startup Kids” - Vòng quanh thế giới của những nhà sáng lập")
    withImgUrl("http://www.action.vn/images/stories/Content_update/121112/607x1204xthe-startup-kids-poster.jpg.pagespeed.ic.CKMTOt8ntv.jpg")
    mustContains("Không khí khởi nghiệp tại Việt Nam đã trở nên nóng hơn bao giờ hết với sự ra đời của các startup công nghệ, cộng đồng trực tuyến, vườn ươm và hỗ trợ rất lớn từ các nhà đầu tư cá nhân hay các quỹ đầu tư mạo hiểm. Nhiều sự kiện về doanh nghiệp đã được thực hiện để tạo ra một hệ sinh thái khởi nghiệp ngày một lớn mạnh và sẵn sàng tương trợ lẫn nhau khi cần thiết. ")
  }

}
