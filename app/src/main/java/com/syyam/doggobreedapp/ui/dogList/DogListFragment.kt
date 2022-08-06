package com.syyam.doggobreedapp.ui.dogList

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialFadeThrough
import com.syyam.doggobreedapp.R
import com.syyam.doggobreedapp.databinding.FragmentDogListBinding
import com.syyam.doggobreedapp.model.FavoriteDog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dog_list.*

@AndroidEntryPoint
class DogListFragment : Fragment(R.layout.fragment_dog_list),
    DogListAdapter.RecyclerViewFavClickListener {

    private lateinit var binding: FragmentDogListBinding
    private val viewModel: DogListViewModel by viewModels()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { DogListAdapter(this) }
    private val args: DogListFragmentArgs by navArgs()
    private var mBreed: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDogListBinding.bind(view)
        val (imageUrl, breed) = args
        mBreed = breed
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        initialize()
        observables()

        viewModel.fetchDogImages(breed.replace(Regex(" "), "").toLowerCase())
    }

    private fun initialize() {
        binding.animationLoading.visibility = View.VISIBLE
        binding.recycler.adapter = adapter
    }

    private fun observables() {
        viewModel.progressBarStatus.observe(viewLifecycleOwner, Observer {
            if (it)
                showLoading(it)
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.animationLoading.visibility = View.GONE
                showError()
            }
        })
        viewModel.favDog.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(context, "Marked As Favourite", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Doggo is already Favourite", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.breedImages.observe(viewLifecycleOwner, Observer {
            adapter.setDogs(it)
        })
    }

    private fun showError() {
        showAnimation(R.raw.error_dog)
    }

    private fun showLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> showAnimation(R.raw.loading)
            false -> animation_loading.visibility = View.GONE
        }
    }

    private fun showAnimation(animationResource: Int) {
        animation_loading.visibility = View.VISIBLE
        animation_loading.setAnimation(animationResource)
        animation_loading.playAnimation()
    }

    override fun onFavClicked(view: View, dog: String) {
        val dog = FavoriteDog(mBreed, dog)
        viewModel.setFavourite(dog)
    }
}