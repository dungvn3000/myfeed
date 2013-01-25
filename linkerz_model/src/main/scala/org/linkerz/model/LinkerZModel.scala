package org.linkerz.model

import org.bson.types.ObjectId

/**
 * The Class LinkerZModel.
 *
 * @author Nguyen Duc Dung
 * @since 1/25/13 5:49 PM
 *
 */
abstract class LinkerZModel(_id: ObjectId) {

  //Convenience method to convert _id to String.
  def id = _id.toString

}
