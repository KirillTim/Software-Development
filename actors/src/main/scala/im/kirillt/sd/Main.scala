package im.kirillt.sd

import akka.actor.{ActorRef, ActorSystem, Props}
import scala.concurrent.duration._
/**
  * Created by kirill on 09.03.17.
  */
object Main extends App {
  val system = ActorSystem("Searcher")
  val master = system.actorOf(MasterActor.props(10 seconds))
  master ! SearchRequest("Blah")
}
