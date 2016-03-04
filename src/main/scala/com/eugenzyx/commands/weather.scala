package com.eugenzyx.commands

import com.eugenzyx.commands.traits._

import scala.io.Source
import scalaj.http.Http
import net.liftweb.json._
import java.util.concurrent.TimeUnit

object Weather extends Command {
  val command = "weather"
  val description = "Get current weather in a city.\nUsage: /weather <city>"

  def handler(sender: Int, args: Seq[String]): String = {
    val city = args.mkString(" ")

    if (city.isEmpty()) "Invalid syntax. See /man weather"
    else {
      implicit val formats = net.liftweb.json.DefaultFormats

      val key = Source.fromFile("./open-weather-map-key").mkString.stripLineEnd
      val response = Http("http://api.openweathermap.org/data/2.5/forecast/weather")
        .param("q", city)
        .param("units", "metric")
        .param("APPID", key)
        .asString

      case class City(id: Int, name: String, country: String)
      case class WeatherInfoItem(id: Int, main: String, description: String, icon: String)
      case class WindInfo(speed: Double)
      // case class WeatherInfo(weather: List[WeatherInfoItem])
      case class MainInfo(temp: Double, temp_min: Double, temp_max: Double)
      case class WeatherList(dt: Int, dt_txt: String, main: MainInfo, weather: WeatherInfoItem, wind: WindInfo)
      case class Weather(city: City, list: List[WeatherList]) {
        override def toString: String = {
          val inputFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
          val outputFormat = new java.text.SimpleDateFormat("E, d MMMM, H")

          val header = s"${ city.name }, ${ city.country }\n\n"
          val today = inputFormat.parse(list.head.dt_txt).getTime()

          val details = list
            .filter(w => TimeUnit.DAYS.convert(inputFormat.parse(w.dt_txt).getTime() - today, TimeUnit.MILLISECONDS) < 2)
            .map(w => s"${ outputFormat.format(inputFormat.parse(w.dt_txt)) }h: ${ math.ceil(w.main.temp).toInt }°C, ${ w.weather.description }").mkString("\n")

          header + details
        }
      }

      val json = parse(response.body)

      json.extract[Weather].toString()
    }
  }
}
