package controllers

import play.api._
import play.api.mvc._
import io.Source
import views.TALPodcast
import models.TALJSON

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def test_podcast = Action{
    val json_str = Source.fromURL(getClass.getResource("/test/pretty.json")).mkString
    Ok(TALPodcast(TALJSON(json_str)).rss)
  }
}