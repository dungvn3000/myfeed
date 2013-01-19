package org.linkerz.model

import org.joda.time.DateTime

/**
 * The Class WebPage.
 *
 * @author Nguyen Duc Dung
 * @since 1/19/13 7:33 PM
 *
 */
case class WebPage(
                    id: String,
                    crawledDate: DateTime,
                    domain: String,
                    contentEncoding: Option[String],
                    score: Option[Double],
                    title: Option[String],
                    text: Option[String],
                    description: Option[String],
                    featureImage: Option[Array[Byte]]
                    ) {

}
