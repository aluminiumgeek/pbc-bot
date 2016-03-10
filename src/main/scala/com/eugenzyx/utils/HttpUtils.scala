package com.eugenzyx.utils

import scalaj.http.{HttpOptions, HttpRequest, Http}

/**
  * Created by eugene on 3/10/16.
  */
object HttpUtils {
  val connectionTimeout = 10000
  val readTimeout = 30000

  def request(url: String): HttpRequest =
    Http(url).timeout(connTimeoutMs = connectionTimeout, readTimeoutMs = readTimeout)
}
