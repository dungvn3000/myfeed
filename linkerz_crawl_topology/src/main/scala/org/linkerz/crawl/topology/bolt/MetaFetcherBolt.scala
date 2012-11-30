package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event.MetaFetch

/**
 * This bolt is using for download meta data relate to a url.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:08 AM
 *
 */
class MetaFetcherBolt extends StormBolt(outputFields = List("metaFetch")){
  def execute(tuple: Tuple) {
    tuple matchSeq {
      case Seq(MetaFetch(feedJob)) => {

      }
    }
  }
}
