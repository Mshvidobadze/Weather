package com.example.weather.helper.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.data.models.PlaceModel
import com.example.weather.databinding.ItemPlacesBinding

class PlacesAdapter()
    : ListAdapter<PlaceModel, PlacesAdapter.PlaceViewHolder>(PlaceChecker())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = ItemPlacesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val currentItem = getItem(position)
        if(currentItem != null){
            holder.bind(currentItem)
        }
    }

    class PlaceViewHolder(private val binding: ItemPlacesBinding):
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(placeModel: PlaceModel){
            binding.apply {
                tvPlaceName.text = placeModel.name
                tvPlacePhenomenon.text = placeModel.phenomenon
                tvPlaceTempMin.text = placeModel.tempmin.toString()
                tvPlaceTempMax.text = placeModel.tempmax.toString()
            }
        }
    }


    class PlaceChecker : DiffUtil.ItemCallback<PlaceModel>(){
        override fun areItemsTheSame(oldItem: PlaceModel, newItem: PlaceModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PlaceModel, newItem: PlaceModel): Boolean {
            return oldItem == newItem
        }

    }

}