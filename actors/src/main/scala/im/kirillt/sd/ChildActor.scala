package im.kirillt.sd

import akka.actor.{Actor, ActorLogging, Props}
import im.kirillt.sd.SearchEngine.SearchEngine
import im.kirillt.sd.http.StubServer

/**
  * Created by kirill on 09.03.17.
  */
class ChildActor(val engine: SearchEngine) extends Actor with ActorLogging {
  override def receive: Receive = {
    case SearchRequest(query) =>
      log.info(s"get $query to $engine")
      val data = StubServer.httpRequest(query, engine)
      sender() ! ChildActorResponse(data, engine)
    case _ =>
      log.info("unknown message")
  }
}

object ChildActor {
  def props(engine: SearchEngine): Props = Props(new ChildActor(engine))
}