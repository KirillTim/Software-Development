package im.kirillt.sd.http

import im.kirillt.sd.SearchEngine
import im.kirillt.sd.SearchEngine.SearchEngine

/**
  * Created by kirill on 09.03.17.
  */
object StubServer {
  def httpRequest(query: String, engine: SearchEngine): Seq[String] = {
    engine match {
      case SearchEngine.Bing =>
        Thread.sleep(5000)
      case _ =>
    }
    (1 to 5).map(pos => s"link $pos from $engine")
  }
}
