package com.eugenzyx.commands.domain.picture

import com.eugenzyx.commands.domain.traits.{Image, Images}

/*
 * Case class used to extract pictures from Google API call's output.
 */
case class Pictures(
  items : List[Picture],
  kind  : String
) extends Images {
  val images: List[Image] = items
}
