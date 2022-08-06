package com.syyam.doggobreedapp.ui.favorite

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syyam.doggobreedapp.R
import com.syyam.doggobreedapp.model.FavoriteDog
import com.syyam.doggobreedapp.util.ImageLoader
import com.syyam.doggobreedapp.util.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_fav.view.image_thumbnail
import kotlinx.android.synthetic.main.item_fav.view.item_container
import kotlinx.android.synthetic.main.item_fav.view.breed_name
import kotlinx.android.synthetic.main.item_fav.view.btn_remove

class FavoriteAdapter(
    val callback: RecyclerViewClickListener
) : ListAdapter<FavoriteDog, FavoriteAdapter.UserDateViewHolder>(UserDataAdapterListDiff()) {

    interface RecyclerViewClickListener {
        fun onRemoveFav(view: View, dog: FavoriteDog)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDateViewHolder {
        return UserDateViewHolder(parent.inflate(R.layout.item_fav))
    }

    override fun onBindViewHolder(holder: UserDateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class UserDataAdapterListDiff : DiffUtil.ItemCallback<FavoriteDog>() {

        override fun areItemsTheSame(oldItem: FavoriteDog, newItem: FavoriteDog): Boolean {
            return oldItem.breed == newItem.breed
        }

        override fun areContentsTheSame(oldItem: FavoriteDog, newItem: FavoriteDog): Boolean {
            return oldItem == newItem
        }
    }

    inner class UserDateViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(dog: FavoriteDog) {

            containerView.breed_name.text = dog.breed.capitalize()
            containerView.btn_remove.setOnClickListener {
                callback.onRemoveFav(
                    itemView,
                    dog
                )
            }
            dog.imageUrl?.let { it1 ->
                ImageLoader.loadImageWithCircularCrop(
                    containerView.context,
                    it1,
                    containerView.image_thumbnail
                )
            }
            this.containerView.item_container.setCardBackgroundColor(Color.YELLOW)
        }
    }
}