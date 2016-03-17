package com.eugenzyx.commands.domain.traits

import java.io.File
import java.net.URL

import info.mukel.telegram.bots.api.InputFile

import sys.process._

/**
  * Trait that represents generic image in a list of images received from an API call.
  *
  * @see [[Images]]
  */
trait Image {
  // The name of the image under which it is saved to disk.
  val fileName: String

  // An url by which the image is available on the Internet.
  val fileUrl: String

  // A title for the image.
  val title: String

  /* Function to download file from an URL.
   *
   * @param url URL of the image to download from.
   */
  def downloadPhoto(url: String): InputFile = {
    val filePath = getPath

    new URL(url) #> new File(filePath) !!

    InputFile(filePath)
  }

  // Downloads the photo and returns [[InputFile]] representation of it.
  def getImage: InputFile = downloadPhoto(fileUrl)

  // Gets unique name for the file to be downloaded.
  private def getPath: String = {
    val downloadedImagesPrefix = "pbcBotImage_"

    def iter(counter: Int = 0): String = {
      val file = new File(s"/tmp/${ downloadedImagesPrefix }${ counter }.jpg")

      if (file.exists) iter(counter + 1) else file.getPath
    }

    iter()
  }
}
