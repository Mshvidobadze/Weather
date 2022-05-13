package com.example.weather.helper.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.data.models.WindModel
import com.example.weather.databinding.ItemPlacesBinding

class WindsAdapter()
    : ListAdapter<WindModel, WindsAdapter.WindViewHolder>(WindChecker())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WindViewHolder {
        val view = ItemPlacesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WindViewHolder(view)
    }

    override fun onBindViewHolder(holder: WindViewHolder, position: Int) {
        val currentItem = getItem(position)
        if(currentItem != null){
            holder.bind(currentItem)
        }
    }

    class WindViewHolder(private val binding: ItemPlacesBinding):
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(windModel: WindModel){
            binding.apply {
                tvPlaceName.text = windModel.name

                tvPlacePhenomenonTitle.text = "Direction:"
                tvPlacePhenomenon.text = windModel.direction

                tvPlaceTempMinTitle.text = "Speed min:"
                tvPlaceTempMin.text = windModel.speedmin.toString()

                tvPlaceTempMaxTitle.text = "Speed max:"
                tvPlaceTempMax.text = windModel.speedmax.toString()
            }
        }
    }


    class WindChecker : DiffUtil.ItemCallback<WindModel>(){
        override fun areItemsTheSame(oldItem: WindModel, newItem: WindModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: WindModel, newItem: WindModel): Boolean {
            return oldItem == newItem
        }

    }

}