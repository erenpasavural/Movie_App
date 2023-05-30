package com.erenpasavural.themovieapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erenpasavural.themovieapp.databinding.ItemHomeRecyclerViewBinding
import com.erenpasavural.themovieapp.model.MovieItem
import com.erenpasavural.themovieapp.util.loadCircleImage


interface MovieClickListener {
    fun onMovieClicked(movieId: Int?)
}

class MovieAdapter(private val movieList: List<MovieItem?>,private val movieClickListener: MovieClickListener) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    class ViewHolder(val binding : ItemHomeRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // Bağlayıcıyı oluştururken kullanılacak layout kaynağını belirle
        val view = ItemHomeRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // Bağlayıcıyı ViewHolder nesnesine bağla
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.textViewMovieTitle.text = movieList[position]?.title
        holder.binding.textViewMovieOverview.text = movieList[position]?.overview
        holder.binding.textViewMovieVote.text = movieList[position]?.voteAverage.toString()

        holder.binding.imageViewMovie.loadCircleImage(movieList[position]?.posterPath)

        holder.binding.root.setOnClickListener{

            movieClickListener.onMovieClicked(movieId = movieList[position]?.id)
        }
    }
}