/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.ai

import org.scalatest.FunSuite
import opennlp.tools.sentdetect.{SentenceDetectorME, SentenceModel}
import java.io.{FileOutputStream, BufferedOutputStream, File, FileInputStream}
import com.google.common.io.Resources
import opennlp.tools.tokenize.{TokenizerME, TokenizerModel}
import opennlp.tools.namefind.{TokenNameFinderEvaluator, NameSampleDataStream, NameFinderME, TokenNameFinderModel}
import java.nio.charset.Charset
import opennlp.tools.util.PlainTextByLineStream

/**
 * The Class TestSentenceDetector.
 *
 * @author Nguyen Duc Dung
 * @since 8/5/12, 10:56 PM
 *
 */

class TestSentenceDetector extends FunSuite {

  val testData = "Pierre Vinken, 61 years old," +
    " will join the board as a nonexecutive director Nov. 29. Mr. Vinken is chairman of " +
    "Elsevier N.V., the Dutch publishing group. Rudolph Agnew, 55 years old and former " +
    "chairman of Consolidated Gold Fields PLC, was named a director of this " +
    "British industrial conglomerate."

  test("testSentenceDetect") {
    val file = new File(Resources.getResource("en-sent.bin").toURI)
    val input = new FileInputStream(file)
    val model = new SentenceModel(input)
    val sentenceDetector = new SentenceDetectorME(model)
    val sentences = sentenceDetector.sentDetect(testData)
    sentences.foreach(println)
    input.close()
  }

  test("testTokenDetect") {
    val file = new File(Resources.getResource("en-token.bin").toURI)
    val input = new FileInputStream(file)
    val model = new TokenizerModel(input)
    val tokenizer = new TokenizerME(model)
    val tokens = tokenizer.tokenize(testData)
    tokens.foreach(println)
    input.close()
  }

  test("testNameFinder") {
    val file = new File(Resources.getResource("en-token.bin").toURI)
    val input = new FileInputStream(file)
    val model = new TokenizerModel(input)
    val tokenizer = new TokenizerME(model)
    val tokens = tokenizer.tokenize(testData)

    val fileNameModel = new File(Resources.getResource("en-ner-person.bin").toURI)
    val inputNameModel = new FileInputStream(fileNameModel)
    val nameModel = new TokenNameFinderModel(inputNameModel)
    val nameFinder = new NameFinderME(nameModel)

    val nameSpans = nameFinder.find(tokens)

    nameSpans.foreach(name => println(name.toString))

    input.close()
    inputNameModel.close()
  }

  test("trainNameModel") {
    val charset = Charset.forName("UTF-8")
    val trainFile = new File(Resources.getResource("en-name.train").toURI)
    val lineStream =
      new PlainTextByLineStream(new FileInputStream(trainFile), charset)

    val sampleStream = new NameSampleDataStream(lineStream)
    val model = NameFinderME.train("en", "person", sampleStream,
      java.util.Collections.emptyMap[String, Object](), 2, 1)
    sampleStream.close()

    val modelOut = new BufferedOutputStream(new FileOutputStream("en-name.bin"))
    model.serialize(modelOut)

    modelOut.close()
  }

  test("testEvaluate") {
    val fileNameModel = new File(Resources.getResource("en-ner-person.bin").toURI)
    val inputNameModel = new FileInputStream(fileNameModel)
    val nameModel = new TokenNameFinderModel(inputNameModel)
    val evaluator = new TokenNameFinderEvaluator(new NameFinderME(nameModel))
    val result = evaluator.getFMeasure

    println(result.toString)
  }

}
