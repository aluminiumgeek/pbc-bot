package com.eugenzyx.commands

import com.eugenzyx.commands.domain.photo._
import com.eugenzyx.commands.traits.{ImageCommand, Command}
import com.eugenzyx.utils.{HttpUtils, Config, Store}

import info.mukel.telegram.bots.api.InputFile
import info.mukel.telegram.bots.api.Message

import scala.concurrent.Future

import net.liftweb.json.parse

object Photo extends Command with ImageCommand {
  val command = "photo"
  val description =
    """Get a photo that matches a patter
      |Usage: /photo [pattern]
      |Looks for a photo by pattern. Iterates through the list of previous results if no pattern is provided.""".stripMargin

  def handler(sender           : Int,
              args             : Seq[String])
             (foundCallback    : (Any, Option[String]) => Future[Message],
              notFoundCallback : String                => Future[Message]): Future[Message] = {

    val pattern = getPattern(args)

    if (pattern.isEmpty) {
      notFoundCallback("No pattern given. No previous results are present.")
    } else {
      val storeKey = s"pbc_photo_$pattern"
      val responseBody: String = Store.client.get(storeKey) match {
        case Some(body:String) =>
          println("Using cached request")
          body
        case None =>
          println("Making request")
          val request = HttpUtils.request("https://api.flickr.com/services/rest/")
            .param("method", "flickr.photos.search")
            .param("format", "json")
            .param("text", pattern)
            .param("api_key", Config("flickr-api-key"))
            .param("nojsoncallback", "true")

          val body = request.asString.body
          Store.client.set(storeKey, body)
          body
      }

      val images = getImages(pattern, responseBody, p => parse(p).extract[Photos])

      respondWithImagesResult(images, foundCallback, notFoundCallback)
    }
  }
}
