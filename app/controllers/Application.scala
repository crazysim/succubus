package controllers

import play.api._
import cache.Cache
import play.api.mvc._
import io.Source
import views.TALPodcast
import models.TALJSON
import libs.concurrent.Akka
import play.api.Play.current

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def test_podcast = Action {
    val json_str = Source.fromURL(getClass.getResource("/test/pretty.json")).mkString
    Ok(TALPodcast(TALJSON(json_str)).rss)
  }

  def podcast = Action {
    val pod_promise = Akka.future {

      Cache.getOrElse[String]("all_data", 3600) {
        import io.Source
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
    Async {
      pod_promise.map(json_str => {
        Ok(TALPodcast(TALJSON(json_str)).rss)
      })
    }
  }
}