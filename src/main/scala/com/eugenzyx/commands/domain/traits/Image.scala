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

  // Full path to the image file.
  val filePath: String

  // An url by which the image is available on the Internet.
  val fileUrl: String

  // A title for the image.
  val title: String

  /* Function to download file from an URL.
   *
   * @param url URL of the image to download from.
   */
  def downloadPhoto(url: String): String = new URL(url) #> new File(filePath) !!

  // Downloads the photo and returns [[InputFile]] representation of it.
  def getPhoto: InputFile = {
    val file = new File(filePath)

    if (!file.exists) downloadPhoto(fileUrl)

    InputFile(filePath)
  }
}
