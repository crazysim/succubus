package models

import org.scala_tools.time.Imports._
import play.api.libs.json._
import org.joda.time.format.ISODateTimeFormat

case class TALPodcast(episodes: List[Episode])

case class Episode(air_date: DateTime, title: String, description: String, episode_number: Int)

object JSONConverter {
  def convert(tal_json: String) = {
    val json = Json.parse(tal_json)
    val json_episode = (json \ "radio_episodes" \\ "radio_episode")
    val ep_list = json_episode.foldLeft(List[Episode]())((l, js) => {
      val orig_date = js \ "original_air_date"
      val air_date = ISODateTimeFormat.dateTimeNoMillis.parseDateTime(orig_date.as[String])
      val title = (js \ "title").as[String]
      val episode_number = (js \ "episode_number").as[Int]
      val description = (js \ "description").as[String]
      l.::(Episode(air_date, title, description, episode_number))
    })
    TALPodcast(ep_list)
  }
}
