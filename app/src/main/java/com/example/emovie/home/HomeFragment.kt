package com.example.emovie.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emovie.R
import com.example.emovie.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: UpcomingMoviesAdapter

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
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initViews() {
        binding.rvUpcomingMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun bindViewModel() {
        viewModel.upcomingMoviesLiveData.observe(viewLifecycleOwner) {
            adapter = UpcomingMoviesAdapter(it)
            binding.rvUpcomingMovies.adapter = adapter
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val errorMsg = getString(R.string.load_error) + ": $it"
            Snackbar.make(binding.root, errorMsg, Snackbar.LENGTH_LONG ).show()
        }
    }

}
