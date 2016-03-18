package com.eugenzyx.commands.traits

import com.eugenzyx.commands.domain.traits.Images
import com.eugenzyx.exceptions.ImageCorruptException
import com.eugenzyx.utils.Store

import info.mukel.telegram.bots.api.{InputFile, Message, PhotoSize}

import scala.collection.mutable.LinkedHashMap
import scala.concurrent.ExecutionContext.Implicits.global
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

  def getImages(pattern: String, response: String, parseFunc: String => Images): Images = {
    if (lastResults.contains(pattern)) {
      println("Cached pattern.")

      lastResults(pattern)
    } else {
      println("Unknown pattern.")

      val photos = parseFunc(response)

      lastResults += (pattern -> photos)
      if (lastResults.size >= 10) lastResults -= lastResults.head._1

      photos
    }
  }

  def respondWithImagesResult(images           : Images,
                              foundCallback    : (Any, Option[String]) => Future[Message],
                              notFoundCallback : String                => Future[Message]): Future[Message] = {
    if (images.images.isEmpty) {
      notFoundCallback("Not found.")
    } else {
      if (images.index >= images.images.length) images.index = 0

      val image = images.images(images.index)

      images.index += 1

      def respondWithError(message: String) =
        notFoundCallback(s"${ message }. Try downloading the image yourself: ${ image.fileUrl }")
      // Check if this image was already sent, so we can prevent duplicate upload
      // and just send photoId to Telegram
      val fileKey = s"file_id_${ image.fileUrl }"
      Store.client.get(fileKey) match {
        case Some(fileId: String) =>
          println("Sending fileId instead of uploading")
          foundCallback(fileId, None)
        case None =>
          println("Uploading image")
          try {
            val result = foundCallback(image.getImage, None)
            // Store fileId
            result.onSuccess {
              case message =>
                if (message.photo.isDefined) {
                  Store.client.set(fileKey, message.photo.get(0).fileId)
                }
            }
            result
          } catch {
            case ioe: IOException =>
              respondWithError("I got an I/O Exception")
            case sslhe: SSLHandshakeException =>
              respondWithError("I got an SSL Handshake Exception")
            case ice: ImageCorruptException =>
              respondWithError("It seems that I have found something other than an image")
            case e: Exception =>
              respondWithError("I got an exception and don't know how to handle it")
          }
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
