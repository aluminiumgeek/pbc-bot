package com.eugenzyx

import com.typesafe.scalalogging.LazyLogging

import org.apache.commons.daemon.Daemon

class PbcBotDaemon extends Daemon with LazyLogging {
  def init(context: org.apache.commons.daemon.DaemonContext): Unit = {
    logger.debug("Initialized successfully.")
    logger.debug(context.toString)
  }

  def destroy(): Unit = {
    logger.debug("Destroyed successfully.")
  }

  def start(): Unit = {
    Bot.run
  }

  def stop(): Unit = {
    Bot.stop
  }
}
