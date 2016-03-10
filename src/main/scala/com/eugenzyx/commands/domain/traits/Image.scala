package com.eugenzyx.commands.domain.traits

import java.io.File
import java.net.URL

import info.mukel.telegram.bots.api.InputFile

import sys.process._

/**
  * Created by eugene on 3/10/16.
  */
trait Image {
  val fileName: String
  val filePath: String
  val fileUrl: String
  val title: String

  def downloadPhoto(url: String, filename: String): String = new URL(url) #> new File(filename) !!

  def getPhoto: InputFile = {
    val file = new File(filePath)

    if (!file.exists) downloadPhoto(fileUrl, filePath)

    InputFile(filePath)
  }
}
