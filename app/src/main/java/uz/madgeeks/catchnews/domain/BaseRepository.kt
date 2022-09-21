package uz.madgeeks.catchnews.domain

import kotlinx.coroutines.flow.Flow
import uz.madgeeks.catchnews.data.base.BaseNetworkResult
import uz.madgeeks.catchnews.data.home.response.TopHeadlines

interface BaseRepository {
    suspend fun getTopHeadlines(country : String): Flow<BaseNetworkResult<TopHeadlines>>

    suspend fun getSearchedArticles(query: String): Flow<BaseNetworkResult<TopHeadlines>>
}