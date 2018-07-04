package example

import org.squeryl.PrimitiveTypeMode


class Song(
  var title: String,
  var artistId: Long,
  var filePath: Option[String]
) extends MusicDbObject with PrimitiveTypeMode {
  def this() = this("", 0, Some(""))

  import MusicDb._

  def artist = artists.where(a => a.id === artistId).single

  def lookupArtist = artists.lookup(artistId)
}
