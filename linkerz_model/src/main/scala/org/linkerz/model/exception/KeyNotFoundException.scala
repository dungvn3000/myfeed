package org.linkerz.model.exception

/**
 * The Class NotFoundException.
 *
 * @author Nguyen Duc Dung
 * @since 1/20/13 2:05 PM
 *
 */
case class KeyNotFoundException(tableName: String, key: String) extends Exception(tableName + " has no key " + key)
