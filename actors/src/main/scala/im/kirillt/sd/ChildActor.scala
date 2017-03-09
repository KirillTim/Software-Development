package im.kirillt.sd

import akka.actor.{Actor, ActorLogging, Props}
import im.kirillt.sd.SearchEngine.SearchEngine

/**
  * Created by kirill on 09.03.17.
  */
class ChildActor(val engine: SearchEngine) extends Actor with ActorLogging {
  override def receive: Receive = {
    case SearchRequest(query) =>
      log.info(s"get $query to $engine")
      engine match {
        case SearchEngine.Bing =>
          log.info("Bing is slow, waiting 10 seconds")
          Thread.sleep(10000) //FIXME
        case _ =>
      }
      sender() ! ChildActorResponse(List(s"ans1 from $engine", s"ans2 from $engine"), engine)
    case _ =>
      log.info("unknown message")
  }
}

object ChildActor {
  def props(engine: SearchEngine): Props = Props(new ChildActor(engine))
}