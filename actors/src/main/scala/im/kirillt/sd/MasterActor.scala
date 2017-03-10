package im.kirillt.sd

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern.{ask, pipe}
import akka.util.Timeout

import scala.concurrent.Future
import scala.util.{Success, Failure}

/**
  * Created by kirill on 09.03.17.
  */
class MasterActor(engineResponseTimeout: Timeout) extends Actor with ActorLogging {

  import scala.concurrent.ExecutionContext.Implicits.global

  implicit val timeout: Timeout = engineResponseTimeout

  override def receive: Receive = {
    case request@SearchRequest(query) =>
      log.info(s"get $query")
      val children = SearchEngine.values.map(e => context.actorOf(ChildActor.props(e))).toList
      val result = children map (c => c ? request) map (f => f.mapTo[ChildActorResponse])
      val resultList = Future.sequence(result).map(list => list.map(charesp => (charesp.engine, charesp.links)).toMap)
      val resp = resultList.map(MasterActorResponse)
      resp.onComplete {
        case Success(s) => context stop self
        case Failure(e) => context stop self
      }
      pipe(resp) to sender()
    case _ =>
      log.info("unknown message")
  }
}

object MasterActor {
  def props(timeout: Timeout): Props = Props(new MasterActor(timeout))
}
