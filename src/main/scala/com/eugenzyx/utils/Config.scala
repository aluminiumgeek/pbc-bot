package com.eugenzyx.utils

import java.io.{FileNotFoundException, File}

import scala.io.Source

/**
  * Created by eugene on 3/10/16.
  */
object Config {
  def apply(value: String): String = {
    val configsPath: String = "./config/"
    val configFile: File = new File(configsPath, value)

    if (configFile.exists) Source.fromFile(configFile).mkString.stripLineEnd else throw new FileNotFoundException
  }
}
