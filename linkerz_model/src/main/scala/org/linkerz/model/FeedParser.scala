package org.linkerz.model

import org.bson.types.ObjectId
import com.novus.salat.dao.SalatDAO

/**
 * The Class FeedParser.
 *
 * @author Nguyen Duc Dung
 * @since 11/16/12 1:37 PM
 *
 */
case class FeedParser
(
  _id: ObjectId = new ObjectId(),
  _feedId: ObjectId,

  titleSelection: String,
  titleAttName: String,
  titleMaxLength: Int,

  contentSelection: String,

  imgSelection: String,

  urlTest1: Option[String] = None,
  title1: Option[String] = None,
  validateText1: Option[String] = None,
  imageUrl1: Option[String] = None,

  urlTest2: Option[String] = None,
  title2: Option[String] = None,
  validateText2: Option[String] = None,
  imageUrl2: Option[String] = None,

  urlTest3: Option[String] = None,
  title3: Option[String] = None,
  validateText3: Option[String] = None,
  imageUrl3: Option[String] = None

  )

object FeedParserDao extends SalatDAO[Link, ObjectId](collection = mongo("feedParser"))
