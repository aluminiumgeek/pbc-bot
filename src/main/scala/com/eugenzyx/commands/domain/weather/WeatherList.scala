package com.eugenzyx.commands.domain.weather

case class WeatherList(
  dt      : Int,
  dt_txt  : String,
  main    : MainInfo,
  weather : WeatherInfoItem,
  wind    : WindInfo
)
