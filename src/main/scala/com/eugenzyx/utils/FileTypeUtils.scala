package com.eugenzyx.utils

import sys.process._

object FileTypeUtils {
  def isJpeg(file: String): Boolean = (s"file $file" !!).contains("JPEG")
}
