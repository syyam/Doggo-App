package com.syyam.doggobreedapp.ui.dogList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syyam.doggobreedapp.databinding.ItemDogBinding
import com.syyam.doggobreedapp.util.ImageLoader

class DogListAdapter(
    val callbackFav: RecyclerViewFavClickListener
) : RecyclerView.Adapter<MainViewHolder>() {

    var dogList = mutableListOf<String>()

    interface RecyclerViewFavClickListener {
        fun onFavClicked(view: View, dog: String)
    }

    fun setDogs(bikes: List<String>) {
        this.dogList = bikes.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDogBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val dog = dogList[position]
        holder.binding.btnFav.setOnClickListener {
            callbackFav.onFavClicked(holder.itemView, dog)

        }
        dog.let { it1 ->
            ImageLoader.loadImage(
                holder.itemView.context,
                it1,
                holder.binding.imageThumbnail
            )
        }
    }

    override fun getItemCount(): Int {
        return dogList.size
    }
}

class MainViewHolder(val binding: ItemDogBinding) : RecyclerView.ViewHolder(binding.root) {

}
