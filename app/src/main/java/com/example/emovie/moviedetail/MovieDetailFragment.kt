package com.example.emovie.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.emovie.R
import com.example.emovie.databinding.FragmentMovieDetailBinding
import com.example.emovie.model.MovieGenre
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MovieDetailFragment: Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView

    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel()
        bindViewModel()
        val args: MovieDetailFragmentArgs by navArgs()
        viewModel.loadMovieDetail(args.movieId)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun bindViewModel() {
        viewModel.movieDetailLiveData.observe(viewLifecycleOwner) { movie ->
            with (binding) {
                Glide.with(ivMoviePoster)
                    .load(PATH_PREFIX + movie.posterPath)
                    .into(ivMoviePoster)
                tvMovieTitle.text = movie.title
                tvReleaseYear.text = movie.releaseDate.substring(0,4)
                tvLanguage.text = movie.language
                val voteText = STAR + movie.voteAvg.toString()
                tvVoteAvg.text = voteText
                tvGenres.text = makeGenreList(movie.genreList)
                tvMoviePlot.text = movie.moviePlot
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val errorMsg = getString(R.string.load_error) + ": $it"
            showErrorMessage(errorMsg)
        }
    }

    private fun makeGenreList(genreList: List<MovieGenre>): String {
        val genreNames = arrayListOf<String>()
        for (g in genreList) {
            genreNames.add(g.name)
        }
        return genreNames.joinToString(separator = DOT) { it }
    }

    private fun showErrorMessage(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG ).show()
    }

    companion object {
        private const val PATH_PREFIX = "https://image.tmdb.org/t/p/w780"
        private const val STAR = " ★ "
        private const val DOT = " ● "
    }

}
