package models
import scala.io.Source

object TestHelper {
  def prettyJson = {
    Source.fromURL(getClass.getResource("/test/pretty.json")).mkString
  }

  def test = {
    "hey due"
  }
}
