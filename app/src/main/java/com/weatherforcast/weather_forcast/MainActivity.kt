package com.weatherforcast.weather_forcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.weatherforcast.weather_forcast.databinding.ActivityMainBinding
import com.weatherforcast.weather_forcast.interfaces.WeatherService
import com.weatherforcast.weather_forcast.model.WeatherBaseModel
import com.weatherforcast.weather_forcast.objects.RetrofitHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var arrayList:ArrayList<String>?=null

    companion object {
        val APP_ID = "61bbf6b35a989d36b96f5b23a7731f45"
        val Lati = "33.6038"
        val Longi = "73.0481"
        val Exclude = "hourly"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val weatherResult = RetrofitHelper.getInstance().create(WeatherService::class.java)

        arrayList= ArrayList()

        // launching a new coroutine
        GlobalScope.launch {
            val result = weatherResult.getCurrentWeatherData(Lati, Longi, Exclude, APP_ID)

            result.enqueue(object : retrofit2.Callback<WeatherBaseModel> {
                override fun onResponse(
                    call: Call<WeatherBaseModel>,
                    response: Response<WeatherBaseModel>
                ) {
                    val weatherResponse = response.body()!!

                    for (i in 0..5) {
                        arrayList?.add(weatherResponse.daily.get(i).temp.day.toString())

                    }
                    binding.tempTxt.text = arrayList?.get(0).toString()
                    binding.temp1Txt.text = arrayList?.get(1).toString()
                    binding.temp2Txt.text = arrayList?.get(2).toString()
                    binding.temp3Txt.text = arrayList?.get(3).toString()
                    binding.dateTxt.text = convertLongToTime(1653452369)

                }
                override fun onFailure(call: Call<WeatherBaseModel>, t: Throwable) = Unit

            })
        }

    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        return format.format(date)
    }

}