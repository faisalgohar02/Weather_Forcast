package com.weatherforcast.weather_forcast.model

data class Weather(
    val description: String,
    val icon: String,
    val id: Double,
    val main: String
)