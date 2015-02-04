package controllers

import play.api._
import cache.Cache
import play.api.mvc._
import views.TALPodcast
import models.TALJSON
import scala.io.Source
import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def test_podcast = Action {
    val json_str = Source.fromURL(getClass.getResource("/test/pretty.json")).mkString
    Ok(TALPodcast(TALJSON(json_str)).rss)
  }

  def podcast = Action.async {
    val pod_promise = scala.concurrent.Future {
      Cache.getOrElse[String]("all_data", 3600) {
        import scala.io.Source
        import java.net.URL

        val tal = "http://www.thisamericanapp.org/api/v2/sync_data/all_data"
        val requestProperties = Map(
          "Authorization" -> "Basic VEFMOklyYUdsYXNz"
        )
        val connection = new URL(tal).openConnection
        requestProperties.foreach({
          case (name, value) => connection.setRequestProperty(name, value)
        })

        Source.fromInputStream(connection.getInputStream).getLines().mkString("\n")
      }
    }

    pod_promise.map(json_str => {
      Ok(TALPodcast(TALJSON(json_str)).rss)
    })
  }
}