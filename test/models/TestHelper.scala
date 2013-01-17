package models
import scala.io.Source

class TestHelper {
  def prettyJson = {
    Source.fromURL(getClass.getResource(res)).mkString
  }
}
