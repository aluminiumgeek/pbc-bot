package com.eugenzyx.utils

import com.redis.RedisClient

object Store {
  val client = new RedisClient(Config.redis("host"), Config.redis("port").toInt)
}
