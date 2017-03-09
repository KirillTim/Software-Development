package im.kirillt.sd

import akka.actor.{Actor, ActorLogging, Props}
import akka.actor.Actor.Receive
import akka.util.Timeout

/**
  * Created by kirill on 09.03.17.
  */
class MasterActor(val timeout: Timeout) extends Actor with ActorLogging {
  override def receive: Receive = {
    case request@SearchRequest(query) =>
      log.info(s"get $query")
      val children = SearchEngine.values.map(e => context.actorOf(ChildActor.props(e)))
      children.foreach(_ ! request)
    case ChildActorResponse(list, engine) =>
      log.info(s"get answer $list from $engine")
    case _ =>
      log.info("unknown message")
  }
}

object MasterActor {
  def props(timeout: Timeout): Props = Props(new MasterActor(timeout))
}
