package com.plinqdevelopers.weatherapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plinqdevelopers.weatherapp.databinding.PlaceItemBinding
import com.plinqdevelopers.weatherapp.domain.model.Place

class PlaceListAdapter(
    private val listener: PlaceItemClickListener,
) : ListAdapter<Place, PlaceListAdapter.PlaceListViewHolder>(PlaceListComparator()) {
    inner class PlaceListViewHolder(
        private val binding: PlaceItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val itemPosition = adapterPosition
                if (itemPosition != RecyclerView.NO_POSITION) {
                    listener.onPlaceItemClicked(
                        placeName = getItem(itemPosition).name,
                    )
                }
            }
        }

        fun bindView(place: Place) {
            binding.apply {
                placeItemName.text = place.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceListViewHolder {
        return PlaceListViewHolder(
            PlaceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: PlaceListViewHolder, position: Int) {
        val placeItem: Place = getItem(position)
        holder.bindView(
            place = placeItem,
        )
    }

    class PlaceListComparator : DiffUtil.ItemCallback<Place>() {
        override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem == newItem
        }
    }

    interface PlaceItemClickListener {
        fun onPlaceItemClicked(
            placeName: String,
        )
    }
}
