package com.syyam.doggobreedapp.ui.dogBreedList

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syyam.doggobreedapp.R
import com.syyam.doggobreedapp.model.Dog
import com.syyam.doggobreedapp.util.ImageLoader
import com.syyam.doggobreedapp.util.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_dog_breed.view.image_thumbnail
import kotlinx.android.synthetic.main.item_dog_breed.view.item_container
import kotlinx.android.synthetic.main.item_dog_breed.view.*

class DogBreedListAdapter(
    val callback: RecyclerViewClickListener
) : ListAdapter<Dog, DogBreedListAdapter.DogViewHolder>(UserDataAdapterListDiff()) {

    interface RecyclerViewClickListener {
        fun onItemClicked(view: View, dog: Dog)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder =
        DogViewHolder(parent.inflate(R.layout.item_dog_breed))

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class UserDataAdapterListDiff : DiffUtil.ItemCallback<Dog>() {

        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.breed == newItem.breed
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
        }
    }

    inner class DogViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(dog: Dog) {

            with(containerView) {
                ViewCompat.setTransitionName(item_container, dog.imageUrl)
                breed_name.text = dog.breed?.capitalize()
                dog.imageUrl?.let { it1 ->
                    ImageLoader.loadImage(
                        containerView.context,
                        it1,
                        image_thumbnail
                    )
                }
                image_thumbnail.setOnClickListener { callback.onItemClicked(itemView, dog) }
            }
        }
    }
}