package models
import scala.io.Source

class TestHelper {
  def getResourceAsString(res: String): String = {
    Source.fromURL(getClass.getResource(res)).mkString
  }
}
