package co.cristian.weatherbold.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
                
                // Show progress indicators initially
                textLoadingIndicator.isVisible = true
                locationName.isVisible = false
                loadingIndicator.isVisible = true
                countryEmoji.isVisible = false
                
                // Set country emoji
                countryEmoji.text = location.getCountryEmoji()
                
                // Simulate loading with minimum 400ms delay for smooth animation
                itemView.post {
                    itemView.handler?.postDelayed({
                        val elapsed = System.currentTimeMillis() - startTime
                        val remainingDelay = (400 - elapsed).coerceAtLeast(0)
                        
                        itemView.handler?.postDelayed({
                            // Hide progress indicators and show content
                            textLoadingIndicator.isVisible = false
                            locationName.isVisible = true
                            locationName.text = location.displayName
                            
                            loadingIndicator.isVisible = false
                            countryEmoji.isVisible = true
                        }, remainingDelay)
                    }, 0)
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
