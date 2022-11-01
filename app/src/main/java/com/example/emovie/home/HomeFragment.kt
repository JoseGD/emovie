package com.example.emovie.home

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emovie.R
import com.example.emovie.databinding.FragmentHomeBinding
import com.example.emovie.home.MoviesAdapter.MoviesViewHolder.Companion.RECOMMENDED
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HomeFragment: Fragment(), PopupMenu.OnMenuItemClickListener {

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

    override fun onMenuItemClick(item: MenuItem): Boolean {
        binding.tvLanguageLabel.text = item.title
        viewModel.filterMoviesByLanguage(item.titleCondensed.toString())
        return true
    }

    private fun initViews() {
        binding.rvUpcomingMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTopRatedMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.tvLanguageLabel.setOnClickListener { showLanguagesPopup(it) }
    }

    private fun bindViewModel() {
        viewModel.upcomingMoviesLiveData.observe(viewLifecycleOwner) {
            upcomingMoviesAdapter = MoviesAdapter(it)
            binding.rvUpcomingMovies.adapter = upcomingMoviesAdapter
        }
        viewModel.topRatedMoviesLiveData.observe(viewLifecycleOwner) {
            topRatedMoviesAdapter = MoviesAdapter(it)
            binding.rvTopRatedMovies.adapter = topRatedMoviesAdapter
        }
        viewModel.recommendedMoviesLiveData.observe(viewLifecycleOwner) {
            recommendedMoviesAdapter = MoviesAdapter(it, RECOMMENDED)
            binding.rvRecommendedMovies.adapter = recommendedMoviesAdapter
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val errorMsg = getString(R.string.load_error) + ": $it"
            Snackbar.make(binding.root, errorMsg, Snackbar.LENGTH_LONG ).show()
        }
    }

    private fun showLanguagesPopup(v: View) {
        PopupMenu(requireContext(), v).apply {
            setOnMenuItemClickListener(this@HomeFragment)
            menuInflater.inflate(R.menu.menu_languages, menu)
            show()
        }
    }

}
