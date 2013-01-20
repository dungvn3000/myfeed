package org.linkerz.model.util

import com.gravity.hbase.schema.Schema
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client.HBaseAdmin
import org.apache.hadoop.hbase.{HColumnDescriptor, HTableDescriptor}

/**
 * The expendable from HPaste Schema.
 *
 * @author Nguyen Duc Dung
 * @since 1/21/13 1:05 AM
 *
 */
abstract class RichSchema(implicit conf: Configuration) extends Schema {

  val admin = new HBaseAdmin(conf)

  def createTable() {
    tables.foreach(table => {
      val desc = new HTableDescriptor(table.tableName)
      table.familyBytes.foreach(family => desc.addFamily(new HColumnDescriptor(family)))
      admin.createTable(desc)
    })
  }

}
