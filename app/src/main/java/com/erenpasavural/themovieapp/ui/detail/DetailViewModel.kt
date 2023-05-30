package com.erenpasavural.themovieapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erenpasavural.themovieapp.model.MovieDetailResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel : ViewModel(){

    val movieResponse : MutableLiveData<MovieDetailResponse> = MutableLiveData()
    val isLoading = MutableLiveData(false)
    val errorMessage : MutableLiveData<String?> = MutableLiveData()

    fun getMovieDetail(movieId: Int) {
        isLoading.value = true

        try {
            viewModelScope.launch{
                val response = com.erenpasavural.themovieapp.network.ApiClient.getClient().getMovieDetail(movieId = movieId.toString(), token = com.erenpasavural.themovieapp.util.Constant.BEARER_TOKEN)

                if(response.isSuccessful) {
                    movieResponse.postValue(response.body())
                } else {

                    if(response.message().isNullOrEmpty()){

                        errorMessage.value = "An unknown error occured"

                    } else {
                        errorMessage.value = response.message()
                    }
                }
            }

        } catch (e:Exception) {
            errorMessage.value = e.message

        } finally {
            isLoading.value = false
        }
    }
}