import org.joda.time.format.DateTimeFormat

package object helpers {
  val RFC2822Format = DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss Z")
}
