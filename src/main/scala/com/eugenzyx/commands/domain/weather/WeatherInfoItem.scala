package com.eugenzyx.commands.domain.weather

case class WeatherInfoItem(
  description : String,
  icon        : String,
  id          : Int,
  main        : String
)
