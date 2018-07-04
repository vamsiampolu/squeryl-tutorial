package example



import org.squeryl.{PrimitiveTypeMode}

class Artist(var name: String) extends MusicDbObject with PrimitiveTypeMode {
  def songs = from(MusicDb.songs){s =>
    where(s.artistId === id) select(s)
  }

  def newSong(title: String, filePath: Option[String]) = {
    MusicDb.songs.insert(
      new Song(title, id, filePath)
    )
  }
}
