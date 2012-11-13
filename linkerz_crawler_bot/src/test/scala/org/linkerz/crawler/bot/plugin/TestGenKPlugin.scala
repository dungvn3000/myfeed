/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin

import parser.GenKPlugin

/**
 * The Class TestGenKPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 9/5/12 11:21 AM
 *
 */
class TestGenKPlugin extends PluginParserTest[GenKPlugin] {

  withParser(new GenKPlugin)

  test("Test 1 GenkPlugin") {
    withUrl("http://genk.vn/pc-do-choi-so/macbook-air-gia-tu-679-usd-tren-apple-store-2012090401511183.chn")
    withTitle("MacBook Air giá từ 679 USD trên Apple Store")
    withImgUrl("http://genk2.vcmedia.vn/Hx5Pawrt3sWkCXFccccccccccccOU/Image/2012/08/apple-macbook-jpg-1346730695_480x0-8dc3f.jpg")
    mustContains("giảm 120 USD so với trước đây. Cấu hình thấp nhất để lựa chọn bao gồm màn hình 11,6 inch, vi xử lý Intel Core 2 Duo")
  }

  test("Test 2 GenkPlugin") {
    withUrl("http://genk.vn/kinh-doanh/nhom-mua-trang-chu-khong-the-truy-cap-chi-nhanh-ha-noi-khong-tiep-nhan-giao-dich-20121113043652157.chn")
    withTitle("Nhóm Mua: Trang chủ không thể truy cập, chi nhánh Hà Nội không tiếp nhận giao dịch")
    withImgUrl("http://genk2.vcmedia.vn/iK7ThcsGBLWxHO9yQ2zbgaejcEBFL/Image/2012/11/nhommua-ac02a.gif")
    mustContains("Một số khách hàng khi đến giao dịch cũng khá bất ngờ khi Nhóm Mua Hà Nội đóng cửa. Tuy nhiên, khi được hỏi hầu hết khách hàng đều không biết thông tin về việc thay đổi lãnh đạo cao cấp của công ty này.")
  }

  test("Test 3 GenkPlugin") {
    withUrl("http://genk.vn/kham-pha/top-10-phat-minh-vi-dai-cua-newton-phan-ii-2012111209456322.chn")
    withTitle("Top 10 phát minh vĩ đại của Newton (Phần II)")
    withImgUrl("http://genk2.vcmedia.vn/N0WoyYblO3QdmZFKPMtKnadHAHTevz/Image/2012/10/newtoncauvong-665dc.jpg")
    mustContains("Nếu bây giờ nghe lý giải như thế thì chúng ta sẽ thấy có vẻ nực cười nhưng thực sự là hồi đó người ta chưa tìm ra được bất cứ lý giải logic nào. Newton đã sử dụng một bóng đèn vào một lăng kính, chạy ánh sáng trắng qua lăng kính để tách nó thành một cầu vồng nhiều màu sắc. Thủ thuật thí nghiệm ánh sáng qua lăng kính không có gì mới nhưmg")
  }

}
