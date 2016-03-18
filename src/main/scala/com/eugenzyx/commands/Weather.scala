package com.eugenzyx.commands

import com.eugenzyx.commands.domain.weather._
import com.eugenzyx.commands.traits.Command
import com.eugenzyx.utils.{HttpUtils, Config}

import net.liftweb.json.parse

object Weather extends Command {
  val command = "weather"
  val description =
    """Get current weather in a city.
      |Usage: /weather <city>""".stripMargin

  def handler(sender: Int, args: Seq[String]): String = {
    val city = args.mkString(" ")

    if (city.isEmpty) {
      "Invalid syntax. See /man weather"
    } else {
      implicit val formats = net.liftweb.json.DefaultFormats

      val response = HttpUtils.request("http://api.openweathermap.org/data/2.5/forecast/weather")
        .param("q", city)
        .param("units", Config.weather("units"))
        .param("APPID", Config.weather("key"))
        .asString

      parse(response.body).extract[WeatherInfo].toString
    }
  }
}
