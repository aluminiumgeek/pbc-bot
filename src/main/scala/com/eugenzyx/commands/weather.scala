package com.eugenzyx.commands

import com.eugenzyx.commands.traits._

import scala.io.Source
import scalaj.http.Http
import net.liftweb.json._

object Weather extends Command {
  val command = "weather"

  val description =
    """
    Get current weather in a city.
    Usage: weather <city>"
    """

  def handler(sender: Int, args: Seq[String]): String =
  {
    val city = args.mkString(" ")

    if (city.isEmpty()) "Invalid syntax. See /man weather"
    else {
      implicit val formats = net.liftweb.json.DefaultFormats

      val key = Source.fromFile("open-weather-map-key").mkString
      val response = Http("http://api.openweathermap.org/data/2.5/forecast/weather")
        .param("q", city)
        .param("units", "metric")
        .param("APPID", key)
        .asString

      case class City(name: String, country: String)
      case class WeatherInfoItem(main: String, description: String)
      case class WindInfo(speed: Double)
      case class WeatherInfo(weather: List[WeatherInfoItem])
      case class MainInfo(temp: Double, temp_min: Double, temp_max: Double)
      case class WeatherList(dt: Int, dt_txt: String, main: MainInfo, weather: WeatherInfo, wind: WindInfo)
      case class Weather(city: City, list: List[WeatherList])

      val output = parse(response.body).extract[Weather]

      output.toString()
    }
  }
}
