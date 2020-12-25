package com.sayaandreas.pupukcompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayaandreas.pupukcompose.model.Movie
import com.sayaandreas.pupukcompose.repository.TMDBRepository
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val tmdbRepo = TMDBRepository()

    private var _nowPlayingList = MutableLiveData(listOf<Movie>())
    val nowPlayingList: LiveData<List<Movie>> = _nowPlayingList

    private var _popularList = MutableLiveData(listOf<Movie>())
    val popularList: LiveData<List<Movie>> = _popularList

    private var _upcomingList = MutableLiveData(listOf<Movie>())
    val upcomingList: LiveData<List<Movie>> = _upcomingList

    private var _topRatedList = MutableLiveData(listOf<Movie>())
    val topRatedList: LiveData<List<Movie>> = _topRatedList


    init {
        viewModelScope.launch {
            val np = tmdbRepo.getNowPlaying()
            val p = tmdbRepo.getPopular()
            val u = tmdbRepo.getUpcoming()
            val tr = tmdbRepo.getTopRated()

            _nowPlayingList.value = np.results.take(7)
            _popularList.value = p.results.take(7)
            _upcomingList.value = u.results.take(7)
            _topRatedList.value = tr.results.take(7)
        }
    }

    fun getMovieDetail(id: Int): Movie? {
        return _nowPlayingList.value?.find { it.id == id }
    }
}