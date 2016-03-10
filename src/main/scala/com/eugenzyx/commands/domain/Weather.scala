package com.eugenzyx.commands.domain

import java.util.concurrent.TimeUnit

/**
  * Created by eugene on 3/10/16.
  */
case class City(id: Int, name: String, country: String)

case class WeatherInfoItem(id: Int, main: String, description: String, icon: String)

case class WindInfo(speed: Double)

case class MainInfo(temp: Double, temp_min: Double, temp_max: Double)

case class WeatherList(dt: Int, dt_txt: String, main: MainInfo, weather: WeatherInfoItem, wind: WindInfo)

case class WeatherInfo(city: City, list: List[WeatherList]) {
  val inputFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  val outputFormat = new java.text.SimpleDateFormat("E, d MMMM, H")

  override def toString: String = {
    val header = s"${ city.name }, ${ city.country }"

    val details = list
      .filter(onlyNearestDays)
      .map(toWeatherLine).mkString("\n")

    s"$header\n\n$details"
  }

  private def onlyNearestDays(w: WeatherList): Boolean = {
    val today = inputFormat.parse(list.head.dt_txt).getTime()

    TimeUnit.DAYS.convert(inputFormat.parse(w.dt_txt).getTime() - today, TimeUnit.MILLISECONDS) < 2
  }

  private def toWeatherLine(w: WeatherList): String =
    s"${ outputFormat.format(inputFormat.parse(w.dt_txt)) }h: ${ math.ceil(w.main.temp).toInt }°C, ${ w.weather.description }"
}
