package com.eugenzyx.commands.domain.weather

import java.util.concurrent.TimeUnit
import java.text.SimpleDateFormat

case class WeatherInfo(
  city : City,
  list : List[WeatherList]
) {
  val inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  val outputFormat = new SimpleDateFormat("E, d MMMM, H")

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
