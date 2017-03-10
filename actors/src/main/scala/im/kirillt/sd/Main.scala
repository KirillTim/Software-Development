package im.kirillt.sd

import akka.actor.{Actor, ActorSystem, Props}
import scala.io.StdIn.readLine

import scala.concurrent.duration._

/**
  * Created by kirill on 09.03.17.
  */
object Main extends App {
  val system = ActorSystem("SearcherApp")
  val master = system.actorOf(MasterActor.props(1 seconds), "Master")
  val local = system.actorOf(Props(new UserIOActor()), "UserIO")

  while (true) {
    println("Enter search query:")
    val cmd = readLine()
    cmd match {
      case "exit" =>
        sys.exit(1)
      case query =>
        master.tell(SearchRequest(query), local)
    }

  }

}

class UserIOActor extends Actor {
  override def receive: Receive = {
    case MasterActorResponse(links) =>
      println("get answer from master:")
      links foreach {it => println(s"${it._1}: ${it._2}")}
    case MasterActorResponse2(links) =>
      println("get answer from master:")
      links foreach(println(_))
    case _ =>
  }
}
