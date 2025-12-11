package co.cristian.weatherbold.presentation.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemDayForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(forecast: DayForecastWithName) {
            binding.apply {
                dayNameText.text = forecast.dayName
                dayConditionText.text = forecast.conditionText
                dayTemperatureText.text = forecast.displayTemp

                // Load weather icon with Coil
                val iconUrl = "https:${forecast.conditionIcon}"
                dayWeatherIcon.load(iconUrl) {
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
