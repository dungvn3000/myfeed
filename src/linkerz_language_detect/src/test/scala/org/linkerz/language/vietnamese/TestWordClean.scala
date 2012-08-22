/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.language.vietnamese

import org.scalatest.FunSuite
import org.linkerz.language.detect.vietnamese.WordClean
import org.linkerz.test.categories.ManualTest
import org.junit.experimental.categories.Category

/**
 * The Class TestWordClean.
 *
 * @author Nguyen Duc Dung
 * @since 8/8/12, 12:46 PM
 *
 */
@Category(Array(classOf[ManualTest]))
class TestWordClean extends FunSuite {

  test("testWordClean") {
    val st1 = WordClean.clean(" Đè bẹp Italy 4-0, Tây Ban Nha giữ ngai vàng Euro ")
    assert(st1 == "Đè bẹp Italy 40 Tây Ban Nha giữ ngai vàng Euro")

    val st2 = WordClean.clean("\"Đè bẹp Italy 4-0, Tây Ban Nha giữ ngai vàng Euro\"")
    assert(st2 == "Đè bẹp Italy 40 Tây Ban Nha giữ ngai vàng Euro")

    val st3 = WordClean.clean("18:46 | 03/08/2012")
    assert(st3 == "1846 03082012")

    val st4 = WordClean.clean("    Xem thêm           Xem theo môn thi đấu    ")
    assert(st4 == "Xem thêm Xem theo môn thi đấu")

    val st5 = WordClean.clean("dungvn3000@gmail.com")
    assert(st5 == "dungvn3000gmail")

    val st6 = WordClean.clean("http://vnexpress.net/")
    assert(st6 == "vnexpress")
  }

}
