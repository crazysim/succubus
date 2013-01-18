package views

import scala.xml.NodeSeq
import models.TALJSON
import helpers._
import org.apache.commons.lang3.StringEscapeUtils
import org.apache.commons.lang3.text.translate.NumericEntityEscaper

case class TALPodcast(rss: NodeSeq)

object TALPodcast {
  def apply(tal_json: TALJSON) = {
    val escp = StringEscapeUtils.ESCAPE_XML
    val xml =
      <rss xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:media="http://search.yahoo.com/mrss/" xmlns:itunes="http://www.itunes.com/dtds/podcast-1.0.dtd" version="2.0">
          <channel>
            <title>This American Life</title>
            <link>http://www.thisamericanlife.org</link>
            <description>Official free, weekly podcast of the award-winning radio show "This American Life." First-person stories and short fiction pieces that are touching, funny, and surprising. Hosted by Ira Glass, from WBEZ Chicago Public Radio. In mp3 and updated Mondays.</description>
            <language>en</language>
            <media:thumbnail url="http://www.thisamericanlife.org/sites/all/play_music/player-logo.png"/>
            <media:category scheme="http://www.itunes.com/dtds/podcast-1.0.dtd">Society &amp; Culture</media:category>
            <media:category scheme="http://www.itunes.com/dtds/podcast-1.0.dtd">Arts</media:category>
            <media:category scheme="http://www.itunes.com/dtds/podcast-1.0.dtd">News &amp; Politics</media:category>
            <itunes:author>Chicago Public Media</itunes:author>
            <itunes:explicit>no</itunes:explicit>
            <itunes:image href="http://www.thisamericanlife.org/sites/all/play_music/player-logo.png"/>
            <itunes:summary>Official free, weekly podcast of the award-winning radio show "This American Life." First-person stories and short fiction pieces that are touching, funny, and surprising. Hosted by Ira Glass, from WBEZ Chicago Public Radio. In mp3 and updated Mondays.</itunes:summary><itunes:category text="Society &amp; Culture"/>
            <itunes:category text="Arts"/>
            <itunes:category text="News &amp; Politics"/>
            {for (episode <- tal_json.episodes) yield
            <item>
              <title>{"#" + episode.episode_number + ": " + episode.title}</title>
              <link>http://www.thisamericanlife.org/radio-archives/episode/{episode.episode_number}/</link>
              <pubDate>{RFC2822Format.print(episode.air_date)}</pubDate>
              <dc:creator>Chicago Public Media</dc:creator>
              <guid isPermaLink="false">{episode.episode_number} from Hacked TAL</guid>
              <media:content url={"http://audio.thisamericanlife.org/jomamashouse/ismymamashouse/" + episode.episode_number + ".mp3"}
                             type="audio/mpeg"/>
              <itunes:author>Chicago Public Media</itunes:author>
              <description>{escp.translate(episode.description)}</description>
              <itunes:subtitle>{escp.translate(episode.description)}</itunes:subtitle>
              <itunes:summary>{escp.translate(episode.description)}</itunes:summary>
              <itunes:explicit>no</itunes:explicit>
              <enclosure url={"http://audio.thisamericanlife.org/jomamashouse/ismymamashouse/" + episode.episode_number + ".mp3"} type="audio/mpeg"/>
              <itunes:duration>{episode.duration.toString}</itunes:duration>
            </item>
            }
          </channel>
        </rss>
    new TALPodcast(xml)
  }
}