package com.absa.weather

import WeatherData
import android.content.Context
import android.location.*
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
class MainActivity : AppCompatActivity() {
    private var locationManager : LocationManager? = null
    val city: String = "pretoria,za"
    val apiKey: String = "647797f25c437eb5539d4e12e182e4bc"
    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create persistent LocationManager reference
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        getWeather()
    }

    private fun getWeather() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getWeather()
        retrofitData.enqueue(object : Callback<WeatherData?> {
            override fun onResponse(call: Call<WeatherData?>?, response: Response<WeatherData?>?) {
                val weatherData = response?.body()
                try {
                    /* Extracting JSON returns from the API */
                    val main = weatherData?.main

                    val sys = weatherData?.sys
                    val wind = weatherData?.wind
                    val weather = weatherData?.weather?.get(0)
                    val updatedAt:Long = (weatherData?.dt?.toLong() ?:Long) as Long
                    val updatedAtText = "Updated at: "+ SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.ENGLISH).format(
                        Date(updatedAt*1000)
                    )
                    val temp = main?.temp.plus("°C")
                    val tempMin = "Min Temp: " + main?.temp_min.plus("°C")
                    val tempMax = "Max Temp: " + main?.temp_max.plus("°C")
                    val pressure = main?.pressure
                    val humidity = main?.humidity
                    val sunrise:Long = (sys?.sunrise?.toLong() ?:Long) as Long
                    val sunset:Long = (sys?.sunset?.toLong() ?:Long) as Long
                    val windSpeed = wind?.speed
                    val weatherDescription = weather!!.description
                    val address = weatherData.name+", "+ sys!!.country

                    // Populating view
                    findViewById<TextView>(R.id.address).text = address
                    findViewById<TextView>(R.id.updated_at).text =  updatedAtText
                    findViewById<TextView>(R.id.status).text = weatherDescription.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }
                    findViewById<TextView>(R.id.temp).text = temp
                    findViewById<TextView>(R.id.temp_min).text = tempMin
                    findViewById<TextView>(R.id.temp_max).text = tempMax
                    findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
                    findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))
                    findViewById<TextView>(R.id.wind).text = windSpeed
                    findViewById<TextView>(R.id.pressure).text = pressure
                    findViewById<TextView>(R.id.humidity).text = humidity

                    // Now showing the data on view
                    findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                    findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

                } catch (e: Exception) {
                    findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                    findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<WeatherData?>?, t: Throwable?) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onStart() {
        // TODO Auto-generated method stub
        super.onStart()
        try {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available")
        }
    }


    //define the listener
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val gcd = Geocoder(context, Locale.getDefault())
            val addresses: List<Address> = gcd.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses.isNotEmpty()) {
                findViewById<TextView>(R.id.address).text = addresses[0].locality
            } else {
                findViewById<TextView>(R.id.address).text = "Location Unavailable"
            }
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private suspend fun getWeatherTask() {
        withContext(Dispatchers.IO) {

        }
    }
}