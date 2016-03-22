package com.eugenzyx.utils

import com.typesafe.scalalogging.LazyLogging

import info.mukel.telegram.bots.api.Update

object HistoryLogger extends LazyLogging {
  private val path = Config.logging("outputFile")
  private val writer = new HadoopWriter(path)

  def log(update: Update): Unit = {
    if (update.message.isDefined) {
      val m = update.message.get
      val u = m.from
      val data = s"${ m.messageId },${ m.date },${ u.id },${ u.lastName },${ u.username },${ m.audio },${m.document},${m.photo},${m.sticker}"

      logger.info(s"Saving event: $data")

      writer.writeLine(data)
    }
  }

  def closeFS(): Unit = writer.closeFileSystem
}
