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
                
                if (flagUrl == null) {
                    loadingIndicator.isVisible = false
                    locationIcon.isVisible = true
                    locationIcon.setImageResource(R.drawable.avatar_12)
                    Log.w("LocationAdapter", "No flag URL for: ${location.country}")
                } else {
                    loadingIndicator.isVisible = true
                    locationIcon.isVisible = false

                    Log.d("LocationAdapter", "Loading: $flagUrl")
                    
                    locationIcon.load(flagUrl) {
                        crossfade(true)
                        placeholder(R.drawable.avatar_12)
                        error(R.drawable.avatar_12)
                        listener(
                            onSuccess = { _, _ ->
                                // Ensure minimum 600ms loading visibility
                                val elapsed = System.currentTimeMillis() - startTime
                                val remainingDelay = (600 - elapsed).coerceAtLeast(0)
                                
                                itemView.post {
                                    itemView.handler?.postDelayed({
                                        binding.loadingIndicator.isVisible = false
                                        binding.locationIcon.isVisible = true
                                        Log.d("LocationAdapter", "Success: ${location.country}")
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
                                        Log.e("LocationAdapter", "Error: ${error.throwable.message}")
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
