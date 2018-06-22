package example

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.adapters.H2Adapter
import org.squeryl.{Session, KeyedEntity, Schema}

class Playlist(
  var name: String,
  var path: String
) extends MusicDbObject {
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
