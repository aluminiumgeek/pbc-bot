package com.eugenzyx.utils

import info.mukel.telegram.bots.api.Update

object Chat {
  private val path = Config.logging("outputFile")
  private val writer = new HadoopWriter(path)

  def log(update: Update): Unit = {
    if (update.message.isDefined) {
      val m = update.message.get

      println(s"${ m.messageId },${ m.date },${ m.from },${ m.audio },${m.document},${m.photo},${m.sticker}")
    }
  }
}
