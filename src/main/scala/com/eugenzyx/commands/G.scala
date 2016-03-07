package com.eugenzyx.commands

import com.eugenzyx.commands.traits.Command
import scala.io.Source
import scalaj.http.Http
import net.liftweb.json.parse

object G extends Command {
  val command = "g"
  val description = "Get the first matching result from Google's search.\nUsage: /g <pattern>"

  def handler(sender: Int, args: Seq[String]): String = {
    implicit val formats = net.liftweb.json.DefaultFormats
    val pattern = args.mkString(" ")

    if (args.isEmpty) {
      "Invalid syntax. See /man g"
    } else {
      val key = Source.fromFile("google-api-key").mkString.stripLineEnd
      val response = Http("http://ajax.googleapis.com/ajax/services/search/web")
        .param("v", "1.0")
        .param("q", pattern)
        .param("key", key)
        .asString

      val results = parse(response.body) \ "responseData" \ "results"

      if (results.children.length == 0) {
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