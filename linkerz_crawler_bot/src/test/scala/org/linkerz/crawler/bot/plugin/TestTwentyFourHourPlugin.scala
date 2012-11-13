/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin

import org.linkerz.crawler.core.factory.{ParserFactory, DefaultDownloadFactory}
import parser.TwentyFourHourParser
import org.junit.{Assert, Test}
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.job.queue.controller.BaseController
import org.linkerz.crawler.core.handler.CrawlerHandler

/**
 * The Class TestTwentyFourHourPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 10/4/12 10:35 PM
 *
 */
class TestTwentyFourHourPlugin extends NewsParserTest[TwentyFourHourParser] {

  withParser(new TwentyFourHourParser)

  test("Test 1 TestTwentyFourHourPlugin"){
    withUrl("http://hcm.24h.com.vn/tin-tuc-trong-ngay/lang-ung-thu-o-ha-noi-c46a488328.html")
    withTitle("\"Thôn ung thư\" ngắc ngoải giữa lòng HN")
    withImgUrl("http://img-hcm.24hstatic.com/upload/4-2012/images/2012-10-04/1349338673_lang-ung-thu-200.jpg")
    mustContains("Vậy nhưng, thôn Xuân Dục ẩn chứa bên trong nó nhiều u uất. Đi sâu vào thôn mới thấy không khí ở đây ảm đạm khác thường. Người Xuân Dục gọi thôn của họ là thôn ung thư. Những năm gần đây, nhiều người ở đây đã lần lượt ra đi vì căn bệnh nan y này.")
  }

  test("Test 2 TestTwentyFourHourPlugin"){
    withUrl("http://hcm.24h.com.vn/am-thuc/ruou-vang-va-nhung-dieu-it-ai-biet-c460a497986.html?utm_source=24h")
    withTitle("Rượu vang và những điều ít ai biết.")
    withImgUrl("http://img-hcm.24hstatic.com/upload/4-2012/images/2012-11-13/1352779886_ruou-vang0.jpg")
    mustContains("Loại thức uống này còn là lựa chọn hàng đầu của những nhà văn nổi tiếng. Ly champagne quả thực đã đi vào thi ca và văn chương thật nhẹ nhàng như cách các quý ông quý bà thưởng thức nó trong những bữa tiệc lãng mạn. Alexandre Dumas cũng đã từng chia sẻ rằng ông chỉ cần nhấm nháp một ly để niềm cảm hứng văn thơ thăng hoa một cách tự nhiên trong ông")
  }

  test("Test 3 TestTwentyFourHourPlugin"){
    withUrl("http://hcm.24h.com.vn/phi-thuong-ky-quac/be-gai-chao-doi-voi-duoi-dai-6cm-c159a497918.html")
    withTitle("Bé gái chào đời với đuôi dài 6cm")
    withImgUrl("http://img-hcm.24hstatic.com/upload/4-2012/images/2012-11-13/1352769148_la3.jpg")
    mustContains("Ban đầu, bà cô bé muốn bà đỡ hoặc một bác sĩ cắt đuôi bằng cách thắt phần thịt này bằng một dây thừng để nó không cung cấp máu song sau đó lại rút lại đề nghị vì sợ biện pháp mạnh có thể ảnh hưởng tới đứa trẻ")
  }

}
