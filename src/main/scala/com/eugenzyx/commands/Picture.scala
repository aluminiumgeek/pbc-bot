package com.eugenzyx.commands

import com.eugenzyx.commands.domain.picture._
import com.eugenzyx.commands.traits.{ImageCommand, Command}
import com.eugenzyx.utils.{HttpUtils, Config}

import info.mukel.telegram.bots.api.InputFile
import info.mukel.telegram.bots.api.Message

import scala.concurrent.Future

import net.liftweb.json.parse

object Picture extends Command with ImageCommand {
  val command = "pic"
  val description =
    """Get a picture that matches a pattern
      |Usage: /pic [pattern]
      |Looks for a picture by pattern. Iterates through the list of previous results if no pattern is provided.""".stripMargin

  def handler(sender           : Int,
              args             : Seq[String])
             (foundCallback    : (InputFile, Option[String]) => Future[Message],
              notFoundCallback : String                      => Future[Message]): Future[Message] = {

    val pattern = getPattern(args)

    if (pattern.isEmpty) {
      notFoundCallback("No pattern given. No previous results are present.")
    } else {
      val request = HttpUtils.request("https://www.googleapis.com/customsearch/v1")
        .param("q", pattern)
        .param("num", Config.google("resultsPerRequest"))
        .param("start", "1")
        .param("imgSize", Config.google("imagesSize"))
        .param("searchType", "image")
        .param("fileType", Config.google("fileType"))
        .param("key", Config.google("key"))
        .param("cx", Config.google("cx"))

      val images = getImages(pattern, request, p => parse(p).extract[Pictures])

      respondWithImagesResult(images, foundCallback, notFoundCallback)
    }
  }
}
