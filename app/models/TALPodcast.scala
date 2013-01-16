package models

import org.scala_tools.time.Imports._
import play.api.libs.json._
import org.joda.time.format.ISODateTimeFormat

case class TALPodcast(episodes: List[Episode])

case class Episode(datetime: DateTime, description: String)

object JSONConverter {
  def convert(tal_json: String) = {
    val json = Json.parse(tal_json)
    val json_episode = (json \ "radio_episodes" \\ "radio_episode")
    val ep_list = json_episode.foldLeft(List[Episode]())((l, js) => {
      val orig_date = js \ "original_air_date"
      val datetime = ISODateTimeFormat.dateTimeNoMillis.parseDateTime(orig_date.as[String])
      val description = (js \ "description").as[String]
      l.::(Episode(datetime, description))
    })
    TALPodcast(ep_list)
  }
}
