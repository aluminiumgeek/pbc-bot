package com.eugenzyx.commands.domain.photo

import com.eugenzyx.commands.domain.traits.{Image, Images}

/*
 * Case class-wrapper used to extract photos from Flickr API call's output.
 */
case class Photos(
  photos : PhotosList,
  stat   : String
) extends Images {
  val images: List[Image] = photos.photo
}
