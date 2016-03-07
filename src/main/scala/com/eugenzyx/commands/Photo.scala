package com.eugenzyx.commands

import com.eugenzyx.commands.traits.Command
import info.mukel.telegram.bots.api.InputFile
import info.mukel.telegram.bots.api.Message
import java.net.URL
import java.io.File
import sys.process._
import scala.collection.mutable.LinkedHashMap
import scala.io.Source
import scala.concurrent.Future
import scalaj.http.Http
import scalaj.http.HttpOptions
import net.liftweb.json.parse

object Photo extends Command {
  val command = "photo"
  val description =
"""
Get a photo that matches a pattern.
Usage: /photo [pattern]
Looks for photos by pattern. Ouputs a random photo from last search result if no pattern given.
"""

  val connectionTimeout = 10000
  val readTimeout = 30000

  val lastResults: LinkedHashMap[String, Photos] = LinkedHashMap()

  case class PhotosList(photo: List[Photo], total: String)
  case class Photos(stat: String, photos: PhotosList) {
    var index: Int = 0
  }
  case class Photo(id: String, secret: String, server: String, farm: Int, title: String) {
    private def downloadPhoto(url: String, filename: String) = new URL(url) #> new File(filename) !!

    val fileName = s"${ id }_${ secret }.jpg"
    val filePath = s"/tmp/$fileName"
    val fileUrl = s"https://farm$farm.staticflickr.com/$server/$fileName"

    def getPhoto: InputFile = {
      downloadPhoto(fileUrl, filePath)

      InputFile(filePath)
    }
  }

  def getPhotos(pattern: String): Photos = {
    implicit val formats = net.liftweb.json.DefaultFormats

    if (lastResults.contains(pattern)) {
      println("Cached pattern.")

      lastResults(pattern)
    } else {
      println("Unknown pattern.")

      val key = Source.fromFile("flickr-api-key").mkString.stripLineEnd
      val response = Http("https://api.flickr.com/services/rest/")
        .param("method", "flickr.photos.search")
        .param("format", "json")
        .param("text", pattern)
        .param("api_key", key)
        .param("nojsoncallback", "true")
        .option(HttpOptions.connTimeout(connectionTimeout))
        .option(HttpOptions.readTimeout(readTimeout))
        .asString

      val photos = parse(response.body).extract[Photos]

      lastResults += (pattern -> photos)
      if (lastResults.size >= 10) lastResults -= lastResults.head._1

      photos
    }
  }

  def handler(
               sender: Int,
               args: Seq[String]
             )
             (
               foundCallback: (InputFile, Option[String]) => Future[Message],
               notFoundCallback: String => Future[Message]
             ): Future[Message] =
  {
    val pattern =
      if (args.isEmpty) {
        if (!lastResults.isEmpty) lastResults.last._1 else ""
      } else {
        args.mkString(" ")
      }

    if (pattern.isEmpty) {
      notFoundCallback("No pattern given. No previous results are present.")
    } else {
      val photosObject = getPhotos(pattern)
      val photos = photosObject.photos.photo

      if (photos.isEmpty) {
        notFoundCallback("Not found.")
      } else {
        val photo = photos(photosObject.index)

        photosObject.index += 1

        foundCallback(photo.getPhoto, Option(s"№${ photosObject.index.toString }: ${ photo.title }"))
      }
    }
  }
}
