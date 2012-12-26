package org.linkerz.parser.util

/**
 * The Class FileUtil.
 *
 * @author Nguyen Duc Dung
 * @since 12/27/12 1:31 AM
 *
 */
object FileUtil {

  def getResourceAsStream(fileName: String) = getClass.getClassLoader.getResourceAsStream(fileName)


}
