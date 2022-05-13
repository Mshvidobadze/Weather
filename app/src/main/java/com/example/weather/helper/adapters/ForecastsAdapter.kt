package com.example.weather.helper.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.data.models.ForecastModel
import com.example.weather.databinding.ItemWeatherBinding
import com.example.weather.helper.listeners.DayClickListener
import kotlinx.android.synthetic.main.item_weather.view.*


class ForecastsAdapter(private val dayClickListener: DayClickListener)
    : ListAdapter<ForecastModel, ForecastsAdapter.ForecastViewHolder>(ForecastChecker())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val currentItem = getItem(position)
        if(currentItem != null){
            holder.bind(currentItem)
            holder.itemView.setOnClickListener { dayClickListener.onDayClick(position, getItem(position)) }
            if(position != 0 && currentItem.day != null && currentItem.night != null){
                holder.itemView.tvTemperatureDay.visibility = View.VISIBLE
                holder.itemView.tvTemperatureNight.visibility = View.VISIBLE
                holder.itemView.ivWeatherIcon.visibility = View.VISIBLE
            }
        }
    }

    class ForecastViewHolder(private val binding: ItemWeatherBinding):
            RecyclerView.ViewHolder(binding.root) {

                @SuppressLint("SetTextI18n")
                fun bind(forecastModel: ForecastModel){
                    binding.apply {
                        tvDate.text = forecastModel.date

                        if(forecastModel.date != "Today" && forecastModel.day != null && forecastModel.night != null){
                            tvTemperatureDay.text = forecastModel.day.tempmax.toString() + "°"
                            tvTemperatureNight.text = forecastModel.night.tempmax.toString() + "°"

                            when {
                                forecastModel.day.phenomenon.contains("shower", true) -> {
                                    ivWeatherIcon.setImageResource(R.drawable.ic_rain)
                                }
                                forecastModel.day.phenomenon.contains("clouds", true) -> {
                                    ivWeatherIcon.setImageResource(R.drawable.ic_cloud)
                                }
                                else -> {
                                    ivWeatherIcon.setImageResource(R.drawable.ic_sun)
                                }
                            }

                        }
                    }
                }
            }


    class ForecastChecker : DiffUtil.ItemCallback<ForecastModel>(){
        override fun areItemsTheSame(oldItem: ForecastModel, newItem: ForecastModel): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: ForecastModel, newItem: ForecastModel): Boolean {
            return oldItem == newItem
        }

    }

}