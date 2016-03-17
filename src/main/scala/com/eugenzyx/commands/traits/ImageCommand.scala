package com.eugenzyx.commands.traits

import com.eugenzyx.commands.domain.traits.Images
import com.eugenzyx.exceptions.ImageCorruptException

import info.mukel.telegram.bots.api.{InputFile, Message}

import scala.collection.mutable.LinkedHashMap
import scala.concurrent.Future
import scalaj.http.HttpRequest

import java.io.IOException
import javax.net.ssl.SSLHandshakeException

/**
  * Created by eugene on 3/10/16.
  */
trait ImageCommand {
  val lastResults: LinkedHashMap[String, Images] = LinkedHashMap()
  implicit val formats = net.liftweb.json.DefaultFormats

  def getImages(pattern: String, request: HttpRequest, parseFunc: String => Images): Images = {
    if (lastResults.contains(pattern)) {
      println("Cached pattern.")

      lastResults(pattern)
    } else {
      println("Unknown pattern.")

      val response = request.asString

      val photos = parseFunc(response.body)

      lastResults += (pattern -> photos)
      if (lastResults.size >= 10) lastResults -= lastResults.head._1

      photos
    }
  }

  def respondWithImagesResult(images           : Images,
                              foundCallback    : (InputFile, Option[String]) => Future[Message],
                              notFoundCallback : String                      => Future[Message]): Future[Message] = {
    if (images.images.isEmpty) {
      notFoundCallback("Not found.")
    } else {
      if (images.index >= images.images.length) images.index = 0

      val image = images.images(images.index)

      images.index += 1

      try {
        foundCallback(image.getImage, None)
      } catch {
        case ioe: IOException =>
          notFoundCallback(s"I got an I/O Excpetion — try downloading an image yourself: ${ image.fileUrl }")
        case sslhe: SSLHandshakeException =>
          notFoundCallback(s"I got an SSL Handshake Excpetion — try downloading an image yourself: ${ image.fileUrl }")
        case ice: ImageCorruptException =>
          notFoundCallback(s"It seems that I have found something other than an image — try downloading it yourself: ${ image.fileUrl }")
        case e: Exception =>
          notFoundCallback("I got an exception and don't know how to handle it!")
      }
    }
  }

  def getPattern(args: Seq[String]): String =
    if (args.isEmpty) {
      if (!lastResults.isEmpty) lastResults.last._1 else ""
    } else {
      args.mkString(" ")
    }
}
