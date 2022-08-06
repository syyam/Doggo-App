package com.syyam.doggobreedapp.ui.dogBreedList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.syyam.doggobreedapp.R
import com.syyam.doggobreedapp.databinding.FragmentDogBreedListBinding
import com.syyam.doggobreedapp.model.Dog
import com.syyam.doggobreedapp.model.ResultWrapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dog_breed_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.LazyThreadSafetyMode.NONE

@AndroidEntryPoint
class DogBreedListFragment : Fragment(R.layout.fragment_dog_breed_list),
    DogBreedListAdapter.RecyclerViewClickListener {

    private lateinit var binding: FragmentDogBreedListBinding
    private val viewModel: DogBreedListViewModel by viewModels()
    private val adapter by lazy(NONE) { DogBreedListAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDogBreedListBinding.bind(view)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        binding.recycler.adapter = adapter
        subscribeObservers()
        initListeners()
        viewModel.setSearchQuery("")
    }

    @SuppressLint("SetTextI18n")
    private fun initListeners() {

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.setSearchQuery(it) }
                return true
            }
        }
        )
        binding.loadMore.setOnClickListener {
            loadMore.text = "Load More Doggos"
            viewModel.fetchDogsFlow()
        }
    }

    @ExperimentalCoroutinesApi
    private fun subscribeObservers() {
        viewModel.snackbar.observe(viewLifecycleOwner, Observer { text ->
            text?.let {
                Snackbar.make(root_layout, text, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarShown()
            }
        })

        viewModel.dogListLiveData.observe(viewLifecycleOwner, Observer {

            adapter.submitList(it)
        })

        viewModel.liveDateFetch.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultWrapper.Loading -> showLoading(it.isLoading)
                is ResultWrapper.NetworkError -> showError()
                is ResultWrapper.Success<*> -> {
                    animation_loading.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun showError() {
        showAnimation(R.raw.error_dog)
    }

    private fun showLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> showAnimation(R.raw.loading)
        }
    }

    private fun showAnimation(animationResource: Int) {
        binding.apply {
            animation_loading.visibility = View.VISIBLE
            animation_loading.setAnimation(animationResource)
            animation_loading.playAnimation()
        }
    }

    override fun onItemClicked(view: View, dog: Dog) {

        exitTransition = Hold().apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_small).toLong()
        }
        val toDogDetailsFragment =
            DogBreedListFragmentDirections.actionDogBreedListFragmentToDogListFragment(
                dog.imageUrl.toString(),
                dog.breed
            )
        val extras = FragmentNavigatorExtras(view to dog.imageUrl.toString())
        navigate(toDogDetailsFragment, extras)
    }

    private fun navigate(destination: NavDirections, extraInfo: FragmentNavigator.Extras) =
        with(findNavController()) {
            currentDestination?.getAction(destination.actionId)
                ?.let { navigate(destination, extraInfo) }
        }
}