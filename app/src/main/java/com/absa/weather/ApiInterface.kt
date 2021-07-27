package com.absa.weather
import WeatherData
import retrofit2.Call
import retrofit2.http.GET
import java.util.*

interface ApiInterface {

    @GET("weather?q=pretoria,za&units=metric&appid=647797f25c437eb5539d4e12e182e4bc")
    fun getWeather(): Call<WeatherData>

}