package com.eugenzyx.commands.domain.picture

import com.eugenzyx.commands.domain.traits.{Image, Images}

/*
 * Case class used to exctract single picture from Google API call's output.
 */
case class Picture(
  link  : String,
  title : String
) extends Image {
  val fileName = link.split("/").last
  val fileUrl  = link
}
