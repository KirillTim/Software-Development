package im.kirillt.sd

import im.kirillt.sd.SearchEngine.SearchEngine

sealed trait Message

case class ChildActorResponse(links: Seq[String], engine: SearchEngine) extends Message

case class MasterActorResponse(links: Map[SearchEngine, Seq[String]]) extends Message

case class SearchRequest(query: String) extends Message