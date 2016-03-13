package com.eugenzyx.commands.domain.photo

/*
 * Case class that represents a list of photos. Used to extract photos
 * from the output of API-call to Flickr.
 */
case class PhotosList(
  photo: List[Photo],
  total: String
)
