package com.weatherforcast.weather_forcast.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherforcast.weather_forcast.databinding.WeatherAdapterItemsBinding
import com.weatherforcast.weather_forcast.model.WeatherUIModel

class WeatherAdapter(val context: Context, var array: ArrayList<WeatherUIModel>) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(WeatherAdapterItemsBinding.inflate(LayoutInflater.from(context), parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.dayTxt.text = array[position].day
        holder.descriptionTxt.text = array[position].description
        holder.minmaxTxt.text = array[position].minmax
        holder.weatherImg.setImageResource(array[position].img)

    }
    override fun getItemCount(): Int {
        return array.size
    }
    class ViewHolder(binding: WeatherAdapterItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        var dayTxt = binding.dayTxt
        var weatherImg = binding.weatherImg
        var descriptionTxt = binding.descriptionTxt
        var minmaxTxt = binding.minmaxTxt
    }

}