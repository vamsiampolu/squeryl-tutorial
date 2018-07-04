package example

import org.squeryl.{KeyedEntity, PrimitiveTypeMode}

object EntryPoint extends PrimitiveTypeMode {

  import MusicDb._

  class MusicDbObject extends KeyedEntity[Long] {
    var id: Long = 0
  }

  class Song(
              var title: String,
              var artistId: Long,
              var filePath: Option[String]
            ) extends MusicDbObject {

    def this() = this("", 0, Some(""))

    def artist = artists.where(a => a.id === artistId).single

    def lookupArtist = artists.lookup(artistId)
  }

  class Artist(var name: String) extends MusicDbObject {

    def songs = from(MusicDb.songs){s =>
      where(s.artistId === id) select(s)
    }

    def newSong(title: String, filePath: Option[String]) = {
     MusicDb.songs.insert{
        new Song(title, id, filePath)
      }
    }
  }

  class PlaylistElement(
                         var songNumber: Int,
                         var playlistId: Long,
                         var songId: Long
                       )

  class Playlist(
                  var name: String,
                  var path: String
                ) extends MusicDbObject with PrimitiveTypeMode {
    import MusicDb._

    def songsInPlaylistOrder = from(playlistElements, songs) { (ple, songs) => {
      where(ple.playlistId === id and ple.songId === songs.id)
      select(songs) orderBy(ple.songNumber asc)
    }
    }

    def addSong(s: Song) = {
      val nextSongNumber: Int = from(playlistElements){ ple => {
        where(ple.playlistId === id)
        compute(nvl(max(ple.songNumber), 0))
      }
      }

      playlistElements.insert(new PlaylistElement(nextSongNumber, id, s.id))
    }

    private def _songCountByArtistId  = {
      from(artists, songs) {
        (a,s) => {
          where(a.id === s.artistId)
          groupBy(a.id)
          compute(count)
        }
      }
    }

    def songCountForAllArtists = {
      from(_songCountByArtistId, artists) { (sca, a) => {
        where(sca.key === a.id)
        select { (a, sca.measures) }
      }
      }
    }

    def songsOf(artistId: Long) = {
      from(playlistElements, songs) { (ple, s) =>
        where(id === ple.playlistId and ple.songId === s.id and s.artistId === artistId)
        select(s)
      }
    }

    def latestSongsOf(artistId: Long) = {
      from(songsOf(artistId)){s => {
        select(s) orderBy(s.id desc)
      }
      }.headOption
    }
  }

}
