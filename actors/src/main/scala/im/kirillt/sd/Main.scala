package im.kirillt.sd

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.concurrent.duration._
/**
  * Created by kirill on 09.03.17.
  */
object Main extends App {
  val system = ActorSystem("SearcherApp")
  val master = system.actorOf(MasterActor.props(2 seconds))
  val local = system.actorOf(Props(new UserIOActor()))
  master.tell(SearchRequest("Blah"), local)
}

class UserIOActor extends Actor {
  override def receive: Receive = {
    case "START" =>
    case MasterActorResponse(links) =>
      println("get answer from master:")
      links foreach (it  => println(s"${it._1}: ${it._2}"))
    case _ =>
  }
}
