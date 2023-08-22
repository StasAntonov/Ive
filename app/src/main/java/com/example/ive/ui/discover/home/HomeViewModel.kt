package com.example.ive.ui.discover.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ive.component.model.DataNews
import com.example.ive.network.ApiResponse
import com.example.ive.network.PhotoNewsPagingSource
import com.example.ive.network.model.toDataNews
import com.example.ive.repository.PhotoRepository
import com.example.ive.utils.NetworkChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    networkChecker: NetworkChecker
): ViewModel() {

    sealed class UiState{
        data class Error(val message: String): UiState()
        object Loading : UiState()
        object None : UiState()
    }
    val isConnected: LiveData<Boolean> = networkChecker

    private val _popular = MutableLiveData<List<DataNews>>()
    val popular: LiveData<List<DataNews>> = _popular

    private var currentPage: Int = 0

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState
    private val pageSize = PAGE_SIZE

    private var pagingSource = PhotoNewsPagingSource(photoRepository)

    lateinit var pagingDataFlow: Flow<PagingData<DataNews>>

    init {
        getPhotoPopular()
        getPhotoNews()
    }

    private fun getPhotoNews(){
        _uiState.postValue(UiState.Loading)
        pagingDataFlow = Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { pagingSource }
        ).flow
            .cachedIn(viewModelScope)
    }

    fun getPhotoPopular(){
        ++currentPage
        _uiState.postValue(UiState.Loading)
        viewModelScope.launch{
            when (val result = photoRepository.getPhotosPopular(
                page = currentPage,
            )) {
                is ApiResponse.Error -> _uiState.postValue(UiState.Error(result.error))
                is ApiResponse.Success -> {
                    _popular.postValue(result.data.map { it.toDataNews() })
                }
            }
        }
    }

    fun refresh(){
        currentPage = 0
        getPhotoPopular()
        getPhotoNews()
    }
    fun resetState(){
        _uiState.postValue(UiState.None)
        _popular.postValue(emptyList())
        getPhotoNews()
    }

    companion object{
        const val PAGE_SIZE = 5
        const val ORDER_BY_POPULAR = "popular"
    }

}