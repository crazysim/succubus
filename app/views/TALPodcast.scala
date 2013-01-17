package views

import scala.xml.NodeSeq
import models.TALJSON

case class TALPodcast(rss: NodeSeq)

object TALPodcast {
  def apply(tal_json: TALJSON) = {
    val xml =
      <?xml version="1.0" encoding="UTF-8"?>
        <rss>
          <channel>
            {for (episode <- tal_json.episodes) yield
            <item>
              <title>
                {episode.title}
              </title>
            </item>}
          </channel>
        </rss>
    ;
    new TALPodcast(xml)
  }
}