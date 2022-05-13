package com.example.weather

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.data.models.CurrentForecastModel
import com.example.weather.data.models.ForecastModel
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.helper.adapters.ForecastsAdapter
import com.example.weather.helper.adapters.PlacesAdapter
import com.example.weather.helper.adapters.WindsAdapter
import com.example.weather.helper.custom.NothingSelectedSpinnerAdapter
import com.example.weather.helper.listeners.DayClickListener
import com.example.weather.helper.util.Resource
import com.example.weather.helper.view_models.ForecastsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_current_forecast_details.*
import kotlinx.android.synthetic.main.layout_current_forecast_details.clCurrentForecastDetails
import kotlinx.android.synthetic.main.layout_forecast_details.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DayClickListener {

    private val viewModel: ForecastsViewModel by viewModels()
    private var localCurrentForecast: CurrentForecastModel? = null
    private val placesAdapter = PlacesAdapter()
    private val windsAdapter = WindsAdapter()
    private val placesAdapterNight = PlacesAdapter()
    private val windsAdapterNight = WindsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val forecastsAdapter = ForecastsAdapter(this)

        binding.apply {
            rvDays?.apply {
                adapter = forecastsAdapter
                layoutManager = LinearLayoutManager(
                    this@MainActivity
                )
            }

            rvPlacesDay?.apply {
                adapter = placesAdapter
                layoutManager = LinearLayoutManager(
                    this@MainActivity
                )
            }

            rvWindsDay?.apply {
                adapter = windsAdapter
                layoutManager = LinearLayoutManager(
                    this@MainActivity
                )
            }

            rvPlacesNight?.apply {
                adapter = placesAdapterNight
                layoutManager = LinearLayoutManager(
                    this@MainActivity
                )
            }

            rvWindsNight?.apply {
                adapter = windsAdapterNight
                layoutManager = LinearLayoutManager(
                    this@MainActivity
                )
            }

            viewModel.currentForecast.observe(this@MainActivity){
                drawCurrentForecast(it.data!!)
                spinnerCityOptions.isEnabled = it is Resource.Success
            }

            viewModel.forecasts.observe(this@MainActivity){
                forecastsAdapter.submitList(viewModel.getCurrentDateItem() + it.data!!.forecasts)
                progressBar!!.isVisible = it is Resource.Loading
                tvErrorText!!.isVisible = it is Resource.Error
                tvErrorText.text = it.error?.localizedMessage
            }
        }
    }

    private fun drawCurrentForecast(currentForecastModel: CurrentForecastModel){
        localCurrentForecast = currentForecastModel

        tvDateMain.text = getString(R.string.today)

        val options: List<String> = currentForecastModel.observations.map { it.name }
        val adapter = ArrayAdapter( this, R.layout.spinner_arrow, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCityOptions.adapter = NothingSelectedSpinnerAdapter(adapter, R.layout.nothing_selected_arrow, this, getString(R.string.select_city))

        spinnerCityOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mOnOptionsSelected(position- 1)
            }
        }

    }

    private fun mOnOptionsSelected(option: Int) {

        if(option >= 0){
            svCurrentForecasts.visibility = View.VISIBLE

            tvName.text = localCurrentForecast!!.observations[option].name
            tvWmocode.text = localCurrentForecast!!.observations[option].wmocode
            tvLongitude.text = localCurrentForecast!!.observations[option].longitude
            tvLatitude.text = localCurrentForecast!!.observations[option].latitude
            tvPhenomenon.text = localCurrentForecast!!.observations[option].phenomenon

            tvVisibility.text = localCurrentForecast!!.observations[option].visibility
            tvPrecipitations.text = localCurrentForecast!!.observations[option].precipitations
            tvAirPressure.text = localCurrentForecast!!.observations[option].airpressure
            tvRelativeHumidity.text = localCurrentForecast!!.observations[option].relativehumidity
            tvAirTemperature.text = localCurrentForecast!!.observations[option].airtemperature

            tvWindDirection.text = localCurrentForecast!!.observations[option].winddirection
            tvWindSpeed.text = localCurrentForecast!!.observations[option].windspeed
            tvWindSpeedMax.text = localCurrentForecast!!.observations[option].windspeedmax
            tvWaterLevel.text = localCurrentForecast!!.observations[option].waterlevel
            tvWaterLevelEH.text = localCurrentForecast!!.observations[option].waterlevel_eh2000

            tvWaterTemperature.text = localCurrentForecast!!.observations[option].watertemperature
            tvUvIndex.text = localCurrentForecast!!.observations[option].uvindex

        }

    }

    override fun onDayClick(index: Int, forecastModel: ForecastModel) {
        tvDateMain.text = forecastModel.date

        if(index > 0){
            currentForecastView.visibility = View.GONE
            forecastView.visibility = View.VISIBLE

            if(forecastModel.day != null){
                tvDayTitle.visibility = View.VISIBLE
                clForecastDetails.visibility = View.VISIBLE
                tvDayPhenomenon.text = forecastModel.day.phenomenon
                tvTempMinDay.text = getString(R.string.celsius, viewModel.convertIntoWords(forecastModel.day.tempmin.toDouble(), "en","US"))
                tvTempMaxDay.text = getString(R.string.celsius, viewModel.convertIntoWords(forecastModel.day.tempmax.toDouble(), "en","US"))
                tvTextDay.text = forecastModel.day.text
                tvSeaDay.text = forecastModel.day.sea
                tvPeipsiDay.text = forecastModel.day.peipsi

                placesAdapter.submitList(forecastModel.day.places)
                windsAdapter.submitList(forecastModel.day.winds)
            }

            if(forecastModel.night != null){
                tvNightTitle.visibility = View.VISIBLE
                clForecastDetailsNight.visibility = View.VISIBLE
                tvNightPhenomenon.text = forecastModel.night.phenomenon
                tvTempMinNight.text = getString(R.string.celsius, viewModel.convertIntoWords(forecastModel.night.tempmin.toDouble(), "en","US"))
                tvTempMaxNight.text = getString(R.string.celsius, viewModel.convertIntoWords(forecastModel.night.tempmax.toDouble(), "en","US"))
                tvTextNight.text = forecastModel.night.text
                tvSeaNight.text = forecastModel.night.sea
                tvPeipsiNight.text = forecastModel.night.peipsi

                placesAdapterNight.submitList(forecastModel.night.places)
                windsAdapterNight.submitList(forecastModel.night.winds)
            }

        }else{
            currentForecastView.visibility = View.VISIBLE
            forecastView.visibility = View.GONE
        }
    }
}