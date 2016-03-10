package com.eugenzyx.commands.domain

import com.eugenzyx.commands.domain.traits.{Image, Images}
import info.mukel.telegram.bots.api.InputFile

/**
  * Created by eugene on 3/10/16.
  */

case class PhotosList(photo: List[Photo], total: String)

case class Photos(stat: String, photos: PhotosList) extends Images {
  val images: List[Image] = photos.photo
}

case class Photo(id: String, secret: String, server: String, farm: Int, title: String) extends Image {
  val fileName = s"${ id }_${ secret }.jpg"
  val filePath = s"/tmp/$fileName"
  val fileUrl  = s"https://farm$farm.staticflickr.com/$server/$fileName"
}