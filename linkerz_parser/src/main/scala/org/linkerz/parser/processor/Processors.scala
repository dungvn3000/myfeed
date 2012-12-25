package org.linkerz.parser.processor

import org.linkerz.parser.model.Article
import collection.mutable.ListBuffer

/**
 * The Class Processors.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 3:37 AM
 *
 */
class Processors extends Processor {

  val processors = new ListBuffer[Processor]

  def process(implicit article: Article) {
    processors.foreach(_.process)
  }

  def <~(processor: Processor) = {
    processors += processor
    this
  }

  def <~(processors: List[Processor]) = {
    this.processors ++= processors
    this
  }

}