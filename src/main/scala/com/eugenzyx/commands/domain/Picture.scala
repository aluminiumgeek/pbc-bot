package com.eugenzyx.commands.domain

import com.eugenzyx.commands.domain.traits.{Image, Images}

/**
  * Created by eugene on 3/10/16.
  */
case class Pictures(kind: String, items: List[Picture]) extends Images {
  val images: List[Image] = items
}

case class Picture(link: String, title: String) extends Image {
  val fileName = link.split("/").last
  val filePath = s"/tmp/$fileName"
  val fileUrl  = link
}