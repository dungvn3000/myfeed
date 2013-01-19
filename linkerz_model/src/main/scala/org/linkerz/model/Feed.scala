/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model


/**
 * The Class Feeds.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 7:47 PM
 *
 */

case class Feed(
                 id: String,
                 groupId: String,

                 name: String,
                 url: String,
                 enable: Boolean,

                 urlRegex: String,
                 excludeUrl: List[String] = Nil,

                 contentSelection: String,
                 removeSelections: List[String]
                 )