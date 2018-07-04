package example


import org.squeryl.{KeyedEntity}

class MusicDbObject extends KeyedEntity[Long] {
  var id: Long = 0
}
