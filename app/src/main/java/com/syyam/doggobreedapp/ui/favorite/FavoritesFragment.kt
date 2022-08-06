package com.syyam.doggobreedapp.ui.favorite

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.transition.MaterialFadeThrough
import com.syyam.doggobreedapp.R
import com.syyam.doggobreedapp.model.FavoriteDog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorites.*

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites), FavoriteAdapter.RecyclerViewClickListener  {

    private val viewModel: FavoriteDogViewModel by viewModels()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { FavoriteAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeLiveData()
        fav_recycler.adapter = adapter
    }

    private fun observeLiveData() {
        viewModel.dogListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.favDogRemoved.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(context, "Removed From Favourites :(", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onRemoveFav(view: View, dog: FavoriteDog) {
        viewModel.removeFav(dog)
    }
}