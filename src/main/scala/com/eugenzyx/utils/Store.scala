package com.eugenzyx.utils

import com.redis.RedisClient

object Store {
  // Host and port should be configurable via config
  val client = new RedisClient("localhost", 6379)
}
