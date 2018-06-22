package example

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.adapters.H2Adapter
import org.squeryl.{Session, KeyedEntity, Schema}

class Artist(var name: String) extends MusicDbObject {
  def songs = from(MusicDb.songs){s =>
    where(s.artistId === id) select(s)
  }

  def newSong(title: String, filePath: Option[String]) = {
    MusicDb.songs.insert(
      new Song(title, id, filePath)
    )
  }
}
