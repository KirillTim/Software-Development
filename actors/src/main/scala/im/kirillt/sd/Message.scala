package im.kirillt.sd

import im.kirillt.sd.SearchEngine.SearchEngine

sealed trait Message

sealed trait ChildActorResponse extends Message

case class ChildActorResponseSuccess(links: Seq[String], engine: SearchEngine) extends ChildActorResponse

case class ChildActorResponseFailed() extends ChildActorResponse

case class MasterActorResponse(links: Map[SearchEngine, Seq[String]]) extends Message

case class MasterActorResponse2(links: List[(SearchEngine, String)]) extends Message

case class SearchRequest(query: String) extends Message