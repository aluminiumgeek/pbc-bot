package com.eugenzyx.utils

import scalaj.http.{HttpOptions, HttpRequest, Http}

/**
  * Created by eugene on 3/10/16.
  */
object HttpUtils {
  val connectionTimeout = Config("connectionTimeout").toInt
  val readTimeout = Config("readTimeout").toInt

  def request(url: String): HttpRequest =
    Http(url).timeout(connTimeoutMs = connectionTimeout, readTimeoutMs = readTimeout)
}
