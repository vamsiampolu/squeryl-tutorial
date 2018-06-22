package example

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.adapters.H2Adapter
import org.squeryl.{Session, KeyedEntity, Schema}

class PlaylistElement(
  var songNumber: Int,
  var playlistId: Long,
  var songId: Long
)
