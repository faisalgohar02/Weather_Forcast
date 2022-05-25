package com.weatherforcast.weather_forcast.model

data class WeatherBaseModel(
    val current: Current,
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    val timezone_offset: Double
)