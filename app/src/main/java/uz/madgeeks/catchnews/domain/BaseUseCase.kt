package uz.madgeeks.catchnews.domain

import kotlinx.coroutines.flow.Flow
import uz.madgeeks.catchnews.data.base.BaseNetworkResult
import uz.madgeeks.catchnews.data.home.response.TopHeadlines
import javax.inject.Inject

class BaseUseCase @Inject constructor(private val homeRepository: BaseRepository) {
    suspend fun getLatestHeadlines(country : String): Flow<BaseNetworkResult<TopHeadlines>> {
        return homeRepository.getTopHeadlines(country)
    }

    suspend fun getSearchedArticles(query : String): Flow<BaseNetworkResult<TopHeadlines>> {
        return homeRepository.getSearchedArticles(query)
    }
}