package org.linkerz

import org.apache.hadoop.hbase.HBaseConfiguration
import com.gravity.hbase.schema.ByteConverter

/**
 * The Class package.
 *
 * @author Nguyen Duc Dung
 * @since 1/20/13 11:44 PM
 *
 */
package object model {

  implicit val conf = HBaseConfiguration.create()

  implicit object ByteArrayConverter extends ByteConverter[Array[Byte]] {
    def toBytes(t: Array[Byte]) = t
    def fromBytes(bytes: Array[Byte], offset: Int, length: Int) = bytes
  }

}
