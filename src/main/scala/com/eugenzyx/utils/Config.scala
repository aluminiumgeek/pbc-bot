package com.eugenzyx.utils

import java.io.File

import uk.co.bigbeeconsultants.bconfig.{Config => BeeConfig}

/**
  * Created by eugene on 3/10/16.
  */
object Config {
  val configPath: String = "./config/bot.conf"
  val configFile: File = new File(configPath)

  require(configFile.exists, "Configuration file should be present!")

  val config = BeeConfig(configFile)

  def apply(value: String): String = section("general")(value)

  def google(value: String): String = section("google")(value)
  def flickr(value: String): String = section("flickr")(value)
  def weather(value: String): String = section("open-weather-map")(value)
  def logging(value: String): String = section("logging")(value)

  def isLoggingEnabled: Boolean = logging("enabled").toBoolean

  private def section(section: String) = config.section(section)
}
