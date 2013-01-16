package models

import org.scala_tools.time.Imports._
import play.api.libs.json._

case class TALPodcast(episodes: List[Episode])

case class Episode(time: DateTime, description: String)

object JSONConverter {
  def convert(tal_json: String) = {
    val json = Json.parse(tal_json)
    val json_episode = (json \ "radio_episodes" \\ "radio_episode")
    val ep_list = json_episode.foldRight(List[Episode]())((js, l) => {
      l
    })
    TALPodcast(ep_list)
  }
}
