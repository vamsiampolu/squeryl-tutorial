package example


import org.squeryl.{Schema}

import EntryPoint._

object MusicDb extends Schema {


  val songs = table[Song]
  val artists = table[Artist]
  val playlists = table[Playlist]
  val playlistElements = table[PlaylistElement]
}
