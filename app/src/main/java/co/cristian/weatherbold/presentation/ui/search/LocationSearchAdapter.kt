package co.cristian.weatherbold.presentation.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import co.cristian.weatherbold.R
import co.cristian.weatherbold.databinding.ItemLocationSearchBinding
import co.cristian.weatherbold.domain.model.Location

/**
 * Adapter for location search results
 * Uses ListAdapter with DiffUtil for efficient updates
 */
class LocationSearchAdapter(
    private val onLocationClick: (Location) -> Unit
) : ListAdapter<Location, LocationSearchAdapter.LocationViewHolder>(LocationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = ItemLocationSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LocationViewHolder(binding, onLocationClick)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class LocationViewHolder(
        private val binding: ItemLocationSearchBinding,
        private val onLocationClick: (Location) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(location: Location) {
            binding.apply {
                val startTime = System.currentTimeMillis()
                
                // Show progress bar for text
                textLoadingIndicator.isVisible = true
                locationName.isVisible = false
                
                // Show progress bar for flag
                val flagUrl = location.getCountryFlagUrl()
                
                Log.d("LocationAdapter", "═══════════════════════════════════════")
                Log.d("LocationAdapter", "📍 Location: ${location.name}, ${location.region}")
                Log.d("LocationAdapter", "🌍 Country: '${location.country}'")
                Log.d("LocationAdapter", "🔗 Flag URL: $flagUrl")
                
                if (flagUrl == null) {
                    loadingIndicator.isVisible = false
                    locationIcon.isVisible = true
                    locationIcon.setImageResource(R.drawable.ic_weather_gradient)
                    Log.w("LocationAdapter", "⚠️ No flag URL generated for country: '${location.country}'")
                    Log.w("LocationAdapter", "   Check CountryFlagUtil mapping")
                } else {
                    loadingIndicator.isVisible = true
                    locationIcon.isVisible = false

                    Log.d("LocationAdapter", "🔄 Attempting to load flag from: $flagUrl")
                    Log.d("LocationAdapter", "   Using Coil ImageLoader...")
                    
                    locationIcon.load(flagUrl) {
                        crossfade(true)
                        placeholder(R.drawable.ic_weather_gradient)
                        error(R.drawable.ic_weather_gradient)
                        listener(
                            onSuccess = { _, _ ->
                                // Ensure minimum 600ms loading visibility
                                val elapsed = System.currentTimeMillis() - startTime
                                val remainingDelay = (600 - elapsed).coerceAtLeast(0)
                                
                                itemView.post {
                                    itemView.handler?.postDelayed({
                                        binding.loadingIndicator.isVisible = false
                                        binding.locationIcon.isVisible = true
                                        Log.d("LocationAdapter", "✅ SUCCESS! Flag loaded for: ${location.country}")
                                        Log.d("LocationAdapter", "   URL: $flagUrl")
                                        Log.d("LocationAdapter", "   Time: ${System.currentTimeMillis() - startTime}ms")
                                    }, remainingDelay)
                                }
                            },
                            onError = { _, error ->
                                // Ensure minimum 600ms loading visibility
                                val elapsed = System.currentTimeMillis() - startTime
                                val remainingDelay = (600 - elapsed).coerceAtLeast(0)
                                
                                itemView.post {
                                    itemView.handler?.postDelayed({
                                        binding.loadingIndicator.isVisible = false
                                        binding.locationIcon.isVisible = true
                                        Log.e("LocationAdapter", "❌ ERROR! Failed to load flag for: ${location.country}")
                                        Log.e("LocationAdapter", "   URL: $flagUrl")
                                        Log.e("LocationAdapter", "   Error Type: ${error.throwable.javaClass.simpleName}")
                                        Log.e("LocationAdapter", "   Error Message: ${error.throwable.message}")
                                        Log.e("LocationAdapter", "   Cause: ${error.throwable.cause?.message}")
                                        Log.e("LocationAdapter", "   Time: ${System.currentTimeMillis() - startTime}ms")
                                        Log.e("LocationAdapter", "   Stack trace:")
                                        error.throwable.printStackTrace()
                                    }, remainingDelay)
                                }
                            }
                        )
                    }
                }
                
                // Show text after minimum 300ms
                itemView.post {
                    itemView.handler?.postDelayed({
                        binding.textLoadingIndicator.isVisible = false
                        binding.locationName.isVisible = true
                        binding.locationName.text = location.displayName
                    }, 600)
                }

                // Click listener
                root.setOnClickListener {
                    onLocationClick(location)
                }
            }
        }
    }

    /**
     * DiffUtil for efficient list updates
     */
    private class LocationDiffCallback : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }
    }
}
