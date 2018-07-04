package example

import org.squeryl.PrimitiveTypeMode

class PlaylistElement(
  var songNumber: Int,
  var playlistId: Long,
  var songId: Long
) extends PrimitiveTypeMode
