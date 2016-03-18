package com.eugenzyx.commands.domain.traits

import com.eugenzyx.utils.Config
import com.eugenzyx.utils.FileTypeUtils
import com.eugenzyx.exceptions.ImageCorruptException

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

    if (FileTypeUtils.isJpeg(filePath)) { InputFile(filePath) } else { throw new ImageCorruptException }
  }

  // Downloads the photo and returns [[InputFile]] representation of it.
  def getImage: InputFile = downloadPhoto(fileUrl)

  // Gets unique name for the file to be downloaded.
  private def getPath: String = {
    val downloadedImagesPrefix = Config("downloadedImagesPrefix")
    val imagesTempDirectory = Config("imagesTempDirectory")
    val extension = Config.google("fileType")

    def iter(counter: Int = 0): String = {
      val file = new File(s"${ imagesTempDirectory }/${ downloadedImagesPrefix }${ counter }.${ extension }")

      if (file.exists) iter(counter + 1) else file.getPath
    }

    iter()
  }
}
