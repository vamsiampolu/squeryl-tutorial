package example

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.adapters.H2Adapter
import org.squeryl.{Session, KeyedEntity, Schema}

class MusicDbObject extends KeyedEntity[Long] {
  var id: Long = 0
}
