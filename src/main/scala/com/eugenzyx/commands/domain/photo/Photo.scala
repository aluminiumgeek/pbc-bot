package com.eugenzyx.commands.domain.photo

import com.eugenzyx.commands.domain.traits.{Image, Images}

import info.mukel.telegram.bots.api.InputFile

/*
 * Case class-wrapper that represents the deepest element of hierarchy of
 * JSON structure in the output of Flickr API call - a photo itself.
 */
case class Photo(
  id     : String,
  farm   : Int,
  secret : String,
  server : String,
  title  : String
) extends Image {
  val fileName = s"${ id }_${ secret }_b.jpg"
  val fileUrl  = s"https://farm$farm.staticflickr.com/$server/$fileName"
}
