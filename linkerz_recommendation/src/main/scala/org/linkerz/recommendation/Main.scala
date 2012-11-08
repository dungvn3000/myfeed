package org.linkerz.recommendation

import breeze.linalg.DenseMatrix
import breeze.text.segment.JavaSentenceSegmenter
import breeze.text.tokenize.PTBTokenizer
import breeze.text.analyze.{CaseFolder, PorterStemmer}
import breeze.text.transform.StopWordFilter
import java.util.Locale

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 11/8/12 1:49 AM
 *
 */
object Main extends App {

  val m = DenseMatrix.zeros[Int](90000, 90000)

//  println(m)
//
//  val text = "Với tỷ lệ tán thành 91%, Quốc hội vừa thông qua Nghị quyết về Phát triển kinh tế xã hội năm 2013, theo đó, " +
//    "đặt mục tiêu lạm phát thấp hơn và tăng trưởng cao hơn so với năm 2012."
//
//  val sentences = (new JavaSentenceSegmenter)(text).toIndexedSeq
//
//  println(sentences)
//
//  val tokenized = sentences.map(PTBTokenizer() ~> StopWordFilter("vi"))
//
//
//  val result = tokenized.map {
//    for (word <- _) yield {
//      CaseFolder(word)
//    }
//  } foreach {
//    _.foreach(println)
//  }



}
