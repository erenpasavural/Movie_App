package com.erenpasavural.themovieapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.erenpasavural.themovieapp.MainActivity
import com.erenpasavural.themovieapp.R
import com.erenpasavural.themovieapp.databinding.FragmentDetailBinding
import com.erenpasavural.themovieapp.ui.home.HomeFragmentDirections
import com.erenpasavural.themovieapp.ui.home.MovieAdapter
import com.erenpasavural.themovieapp.ui.home.MovieClickListener
import com.erenpasavural.themovieapp.util.loadImage

class DetailFragment : Fragment() {
    private var _binding:FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(inflater,container,false)

        viewModel.getMovieDetail(movieId = args.movieId)
        observeEvents()

        return binding.root
    }

    private fun observeEvents() {

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            binding.textViewErrorDetail.text = error
            binding.textViewErrorDetail.isVisible = true
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBarDetail.isVisible = loading
        }

        viewModel.movieResponse.observe(viewLifecycleOwner) { movie ->

            binding.imageViewDetail.loadImage(movie.backdropPath)

            binding.textViewDetailVote.text = movie.voteAverage.toString()
            binding.textViewDetailStudio.text = movie.productionCompanies?.first()?.name
            binding.textViewDetailLanguage.text = movie.spokenLanguages?.first()?.englishName

            binding.textViewDetailOverview.text = movie.overview

            binding.movieDetailGroup.isVisible = true

            (requireActivity() as MainActivity).supportActionBar?.title = movie.title
        }
        
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}