package uz.madgeeks.catchnews.presentation.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.madgeeks.catchnews.data.base.BaseNetworkResult
import uz.madgeeks.catchnews.data.home.response.Article
import uz.madgeeks.catchnews.data.home.response.TopHeadlines
import uz.madgeeks.catchnews.domain.BaseUseCase
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val mainUseCase: BaseUseCase,
) : ViewModel() {
    private val latestHeadlines = MutableLiveData<TopHeadlines>()
    val latestHeadlinesLiveData: LiveData<TopHeadlines> get() = latestHeadlines

    private val searchedArticles = MutableLiveData<List<Article>>()
    val searchedArticlesLiveData: LiveData<List<Article>> get() = searchedArticles

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    fun getLatestHeadlines(country: String) {
        viewModelScope.launch {
            mainUseCase.getLatestHeadlines(country).catch {
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { item ->
                            latestHeadlines.value = item
                        }
                    }
                    is BaseNetworkResult.Error -> {
                        result.message.let {
                            _errorLiveData.value = it
                        }
                    }
                    is BaseNetworkResult.Loading -> {
                        result.isLoading?.let {
                            _isLoadingLiveData.value = it
                        }
                    }
                }
            }
        }
    }

    fun getSearchedArticles(query: String) {
        viewModelScope.launch {
            mainUseCase.getSearchedArticles(query).catch {
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { item ->
                            searchedArticles.value = item.articles
                        }
                    }
                    is BaseNetworkResult.Error -> {
                        result.message.let {
                            _errorLiveData.value = it
                        }
                    }
                    is BaseNetworkResult.Loading -> {
                        result.isLoading?.let {
                            _isLoadingLiveData.value = it
                        }
                    }
                }
            }
        }
    }
}