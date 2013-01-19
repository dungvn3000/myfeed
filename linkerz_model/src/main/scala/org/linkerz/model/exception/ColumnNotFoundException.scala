package org.linkerz.model.exception


/**
 * The Class ColumnNotFoundException.
 *
 * @author Nguyen Duc Dung
 * @since 1/19/13 7:51 PM
 *
 */
case class ColumnNotFoundException(tableName: String, colName: String) extends Exception(tableName + " has no " + colName + " column")