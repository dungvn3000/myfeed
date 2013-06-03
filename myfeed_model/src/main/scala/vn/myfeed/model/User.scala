package vn.myfeed.model

import org.bson.types.ObjectId

/**
 * The Class User.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:05 PM
 *
 */

case class User(
                 _id: ObjectId = new ObjectId,
                 username: String,
                 password: String,
                 email: String,
                 role: String
                 ) extends BaseModel(_id)