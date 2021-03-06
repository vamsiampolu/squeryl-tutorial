package example

import org.squeryl.adapters.H2Adapter
import org.squeryl.{KeyedEntity, PrimitiveTypeMode, Schema, Session}

object Main extends App with PrimitiveTypeMode {
  import MusicDb._

  Class.forName("org.h2.Driver")
  val session = Session.create(
    java.sql.DriverManager.getConnection("jdbc:h2:~/test", "sa", ""),
    new H2Adapter
  )

  transaction {
     test
  }

  /*try {
    session.work {
      test
      session.connection.commit
    }
  } catch {
    case e: Exception => session.connection.rollback
  }
  */

  def test = {
    val herbyHancock = artists.insert(new Artist("Herby Hancock"))
    val ponchoSanchez = artists.insert(new Artist("Poncho Sanchez"))
    val mongoSantaMaria = artists.insert(new Artist("Mongo Santa Maria"))
    val watermelonMan = herbyHancock.newSong("Watermelon Man", None)
    val besameMama = mongoSantaMaria.newSong("Besame Mama", Some("c:/MyMusic/besameMama.flac"))
    val freedomSound = ponchoSanchez.newSong("Freedom Sound", None)
  }
}
