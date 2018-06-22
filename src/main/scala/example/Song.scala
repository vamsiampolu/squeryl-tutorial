package example

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.adapters.H2Adapter
import org.squeryl.{Session, KeyedEntity, Schema}

class Song(
  var title: String,
  var artistId: Long,
  var filePath: Option[String]
) extends MusicDbObject {
  def this() = this("", 0, Some(""))

  import MusicDb._

  def artist = artists.where(a => a.id === artistId).single

  def lookupArtist = artists.lookup(artistId)
}
