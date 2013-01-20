package org.linkerz.model

import java.util.Date
import org.joda.time.DateTime

/**
 * The Class NewsBox.
 *
 * @author Nguyen Duc Dung
 * @since 11/11/12 2:25 AM
 *
 */
case class NewsBox(
                    id: String,
                    userId: String,
                    webPageId: String,
                    groupName: String,
                    clicked: Boolean = false,
                    createdDate: DateTime = new DateTime()
                    )