package com.eugenzyx.commands

import com.eugenzyx.commands.domain.Pictures
import com.eugenzyx.commands.traits.{ImageCommand, Command}
import com.eugenzyx.utils.{HttpUtils, Config}

import info.mukel.telegram.bots.api.InputFile
import info.mukel.telegram.bots.api.Message

import scala.concurrent.Future

import net.liftweb.json.parse

object Picture extends Command with ImageCommand {
  val command = "pic"
  val description =
    """Get a picture that matches a patter
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
        .param("num", "10")
        .param("start", "1")
        .param("imgSize", "large")
        .param("searchType", "image")
        .param("fileType", "jpg")
        .param("key", Config("google-api-key"))
        .param("cx", Config("google-cx"))

      val images = getImages(pattern, request, p => parse(p).extract[Pictures])

      respondWithImagesResult(images, foundCallback, notFoundCallback)
    }
  }
}
