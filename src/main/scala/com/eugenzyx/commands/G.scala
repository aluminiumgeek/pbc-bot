package com.eugenzyx.commands

import com.eugenzyx.commands.traits.Command
import com.eugenzyx.utils.Config
import scalaj.http.Http
import net.liftweb.json.parse

object G extends Command {
  val command = "g"
  val description =
    """Get the first matching result from Google's search.
      |Usage: /g <pattern>""".stripMargin

  def handler(sender: Int, args: Seq[String]): String = {
    implicit val formats = net.liftweb.json.DefaultFormats
    val pattern = args.mkString(" ")

    if (args.isEmpty) {
      "Invalid syntax. See /man g"
    } else {
      val response = Http("http://ajax.googleapis.com/ajax/services/search/web")
        .param("v", "1.0")
        .param("q", pattern)
        .param("key", Config("google-api-key"))
        .asString

      val results = parse(response.body) \ "responseData" \ "results"

      if (results.children.isEmpty) {
        "Not found."
      } else {
        val title = (results(0) \ "titleNoFormatting").extract[String]
        val url = (results(0) \ "unescapedUrl").extract[String]

        val output = s"$title\n\n$url"
        output
      }
    }
  }
}
