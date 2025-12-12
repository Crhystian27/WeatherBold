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
                
                // Set country emoji using Location's method
                countryEmoji.text = location.getCountryEmoji()
                
                // Simulate loading with minimum delay for smooth animation
                // This delay ensures the loading animation is visible to the user
                itemView.post {
                    itemView.handler?.postDelayed({
                        val elapsed = System.currentTimeMillis() - startTime
                        val remainingDelay = (MIN_LOADING_ANIMATION_DELAY_MS - elapsed).coerceAtLeast(0)
                        
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
        
        companion object {
            /**
             * Minimum delay to ensure loading animation is visible
             * This provides better UX by showing a smooth transition
             */
            private const val MIN_LOADING_ANIMATION_DELAY_MS = 400L
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
