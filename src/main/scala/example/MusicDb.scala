package example

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.adapters.H2Adapter
import org.squeryl.{Session, KeyedEntity, Schema}

object MusicDb extends Schema {
  val songs = table[Song]
  val artists = table[Artist]
  val playlists = table[Playlist]
  val playlistElements = table[PlaylistElement]
}
