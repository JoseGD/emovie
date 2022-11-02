package com.example.emovie.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emovie.R
import com.example.emovie.databinding.FragmentHomeBinding
import com.example.emovie.home.MoviesAdapter.MoviesViewHolder.Companion.RECOMMENDED
import com.example.emovie.model.ListedMovie
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView

    private lateinit var viewModel: HomeViewModel
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var recommendedMoviesAdapter: MoviesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel()
        initViews()
        bindViewModel()
        viewModel.loadUpcomingMovies()
        viewModel.loadTopRatedMovies()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initViews() {
        with (binding) {
            rvUpcomingMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvTopRatedMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            tvLanguageLabel.setOnClickListener { showLanguagesPopup(it) }
            tvLaunchYearLabel.setOnClickListener { showYearsPopup(it) }
        }
    }

    private fun bindViewModel() {
        viewModel.upcomingMoviesLiveData.observe(viewLifecycleOwner) {
            upcomingMoviesAdapter = MoviesAdapter(it) { movie ->
                navigateToDetailScreen(movie)
            }
            binding.rvUpcomingMovies.adapter = upcomingMoviesAdapter
        }
        viewModel.topRatedMoviesLiveData.observe(viewLifecycleOwner) {
            topRatedMoviesAdapter = MoviesAdapter(it) { movie ->
                navigateToDetailScreen(movie)
            }
            binding.rvTopRatedMovies.adapter = topRatedMoviesAdapter
        }
        viewModel.recommendedMoviesLiveData.observe(viewLifecycleOwner) { (list, scroll) ->
            if (list.isNotEmpty()) {
                recommendedMoviesAdapter = MoviesAdapter(list, RECOMMENDED) { movie ->
                    navigateToDetailScreen(movie)
                }
                binding.rvRecommendedMovies.adapter = recommendedMoviesAdapter
                // Scrolling only when reloading recommended list after filtering
                if (scroll) scrollABit()
            } else {
                showErrorMessage(getString(R.string.no_movies_found))
                resetLanguageFilter()
                resetYearFilter()
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val errorMsg = getString(R.string.load_error) + ": $it"
            showErrorMessage(errorMsg)
        }
    }

    private fun showLanguagesPopup(v: View) {
        PopupMenu(requireContext(), v).apply {
            menuInflater.inflate(R.menu.menu_languages, menu)
            setOnMenuItemClickListener { item ->
                binding.tvLanguageLabel.text = item.title
                resetYearFilter()
                viewModel.filterMoviesByLanguage(item.titleCondensed.toString())
                true
            }
            show()
        }
    }

    private fun showYearsPopup(v: View) {
        PopupMenu(requireContext(), v).apply {
            for (year in 1970..2022) {
                menu.add(year.toString())
            }
            setOnMenuItemClickListener { item ->
                binding.tvLaunchYearLabel.text = item.title
                resetLanguageFilter()
                viewModel.filterMoviesByReleaseYear(item.title.toString())
                true
            }
            show()
        }
    }

    private fun navigateToDetailScreen(movie: ListedMovie) {
        val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment()
        action.movieId = movie.id ?: 0
        findNavController().navigate(action)
    }

    // TODO use a real animation for this
    private fun scrollABit() {
        with (binding.nsvHome) {
            postDelayed({
                smoothScrollTo(0, 700)   // Move the screen down a bit to show the effect of filtering results
            }, 1000)
        }
    }

    private fun resetLanguageFilter() {
        binding.tvLanguageLabel.text = getString(R.string.language)
    }

    private fun resetYearFilter() {
        binding.tvLaunchYearLabel.text = getString(R.string.launch_year)
    }

    private fun showErrorMessage(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG ).show()
    }

}
