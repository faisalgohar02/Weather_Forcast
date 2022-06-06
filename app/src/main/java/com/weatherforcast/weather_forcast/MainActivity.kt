package com.weatherforcast.weather_forcast

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherforcast.weather_forcast.adapters.WeatherAdapter
import com.weatherforcast.weather_forcast.databinding.ActivityMainBinding
import com.weatherforcast.weather_forcast.interfaces.WeatherService
import com.weatherforcast.weather_forcast.model.WeatherBaseModel
import com.weatherforcast.weather_forcast.model.WeatherUIModel
import com.weatherforcast.weather_forcast.objects.RetrofitHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var arrayList: ArrayList<WeatherUIModel>

    companion object {
        val APP_ID = ""
        val Lati = "33.6038"
        val Longi = "73.0481"
        val Exclude = "hourly"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val weatherResult = RetrofitHelper.getInstance().create(WeatherService::class.java)

        arrayList = ArrayList()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.setItemViewCacheSize(10)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        if (isInternetAvailable) {
            // launching a new coroutine
            GlobalScope.launch {
                val result = weatherResult.getCurrentWeatherData(Lati, Longi, Exclude, APP_ID)
                result.enqueue(object : retrofit2.Callback<WeatherBaseModel> {
                    override fun onResponse(
                        call: Call<WeatherBaseModel>,
                        response: Response<WeatherBaseModel>
                    ) {
                        val weatherResponse = response.body()!!

                        for (i in 0..6) {
                            var minStr =
                                calculateTem(weatherResponse!!.daily!!.get(i).temp.min - 273.15)
                            var maxStr =
                                calculateTem(weatherResponse!!.daily!!.get(i).temp.max - 273.15)
                            var descriptionStr =
                                weatherResponse!!.daily!!.get(i)!!.weather!!.get(0).description
                            var iconStr =
                                checkIcons(weatherResponse!!.daily!!.get(i)!!.weather!!.get(0).icon)
                            var dayNameStr =
                                getDay(weatherResponse!!.daily!!.get(i)!!.dt.toDouble())
                            arrayList.add(
                                WeatherUIModel(
                                    dayNameStr,
                                    iconStr,
                                    descriptionStr,
                                    "$minStr / $maxStr"
                                )
                            )
                        }
                        binding.recyclerView.adapter = WeatherAdapter(this@MainActivity, arrayList)
                    }

                    override fun onFailure(call: Call<WeatherBaseModel>, t: Throwable) = Unit
                })
            }
        } else {
            Toast.makeText(this@MainActivity, "Internet connection not found", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private val isInternetAvailable: Boolean
        @SuppressLint("MissingPermission")
        get() {
            val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null
        }

    private fun getDay(milli: Double): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = ((milli * 1000L).toLong())
        val date = Date(calendar.timeInMillis)
        return sdf.format(date)
    }

    private fun getTime(timestamp: Double): String {
        val cal1 = Calendar.getInstance()
        cal1.timeInMillis = ((timestamp * 1000L).toLong())
        val dateFormat = SimpleDateFormat("hh:mm a")
        return dateFormat.format(cal1.time)
    }

    private fun getDate(timestamp: Double): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = (timestamp * 1000L).toLong()
        return DateFormat.format("dd-MM-yyyy", calendar).toString()
    }

    private fun calculateTem(tem: Double): String {
        return "${String.format("%s ", DecimalFormat("###").format(tem))}° "
    }

    private fun checkIcons(iconID: String): Int {

        if (iconID.equals("01d") || iconID.equals("01n")) {
            return R.drawable.ic_sun
        } else if (iconID.equals("02d") || iconID.equals("02n")) {
            return R.drawable.ic_sun
        } else if (iconID.equals("03d") || iconID.equals("03n")) {
            return R.drawable.ic_sun
        } else if (iconID.equals("04d") || iconID.equals("04n")) {
            return R.drawable.ic_sun
        } else if (iconID.equals("09d") || iconID.equals("09n")) {
            return R.drawable.ic_sun
        } else if (iconID.equals("10d") || iconID.equals("10n")) {
            return R.drawable.ic_sun
        } else if (iconID.equals("11d") || iconID.equals("11n")) {
            return R.drawable.ic_sun
        } else {
            return R.drawable.ic_sun
        }
    }

}