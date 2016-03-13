package com.eugenzyx.commands.domain.traits

/**
  * Represents a list of images in which all the results are present.
  *
  * @see [[Image]]
  */
trait Images {
  // Index that is used to iterate through images in the list. Stores position
  // at which user left off browsing results of a single pattern.
  var index: Int = 0

  // An actual list of images-results.
  val images: List[Image]
}
