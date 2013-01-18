package models

import org.scala_tools.time.Imports._
import play.api.libs.json._
import org.joda.time.format.ISODateTimeFormat
import xml.NodeSeq
import org.apache.commons.lang3.StringEscapeUtils

case class TALJSON(episodes: List[Episode])


case class Episode(air_date: DateTime, title: String, description: String, episode_number: Int, duration: Int)

object TALJSON {
  def apply(tal_json: String) = {
    val json = Json.parse(tal_json)
    val json_episode = (json \ "radio_episodes" \\ "radio_episode")
    val ep_list = json_episode.foldLeft(List[Episode]())((l, js) => {
      val orig_date = js \ "original_air_date"
      val air_date = ISODateTimeFormat.dateTimeNoMillis.parseDateTime(orig_date.as[String])
      val title = (js \ "title").as[String]
      val episode_number = (js \ "episode_number").as[Int]
      val description = StringEscapeUtils.unescapeHtml4(
        (js \ "description").as[String]
      )
      val duration = (js \ "duration").as[Int]
      l.::(Episode(air_date, title, description, episode_number, duration))
    })
    new TALJSON(ep_list.reverse)
  }
}
