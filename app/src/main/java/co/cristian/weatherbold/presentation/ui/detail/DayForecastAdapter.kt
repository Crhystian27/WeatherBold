package co.cristian.weatherbold.presentation.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.cristian.weatherbold.core.util.WeatherFormatter
import co.cristian.weatherbold.databinding.ItemDayForecastBinding
import co.cristian.weatherbold.domain.model.DayForecastWithName
import coil.load

class DayForecastAdapter : ListAdapter<DayForecastWithName, DayForecastAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDayForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    class ViewHolder(
        private val binding: ItemDayForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(forecast: DayForecastWithName, position: Int) {
            binding.apply {
                val context = binding.root.context
                
                // Use localized day names from resources
                dayNameText.text = WeatherFormatter.getDayName(context, position)
                dayConditionText.text = forecast.conditionText
                dayTemperatureText.text = WeatherFormatter.formatTemperature(context, forecast.avgTempCelsius)

                // Load weather icon with Coil
                dayWeatherIcon.load(forecast.conditionIcon) {
                    placeholder(android.R.drawable.ic_menu_gallery)
                    error(android.R.drawable.ic_dialog_alert)
                    crossfade(true)
                }
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<DayForecastWithName>() {
        override fun areItemsTheSame(
            oldItem: DayForecastWithName,
            newItem: DayForecastWithName
        ): Boolean = oldItem.date == newItem.date

        override fun areContentsTheSame(
            oldItem: DayForecastWithName,
            newItem: DayForecastWithName
        ): Boolean = oldItem == newItem
    }
}
