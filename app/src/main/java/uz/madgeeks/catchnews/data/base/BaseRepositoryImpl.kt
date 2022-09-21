package uz.madgeeks.catchnews.data.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.madgeeks.catchnews.data.home.response.TopHeadlines
import uz.madgeeks.catchnews.domain.BaseRepository
import javax.inject.Inject

class BaseRepositoryImpl @Inject constructor(private val service: BaseService) :
    BaseRepository {
    override suspend fun getTopHeadlines(country : String): Flow<BaseNetworkResult<TopHeadlines>> {
        return flow {
            emit(BaseNetworkResult.Loading(true))
            val response = service.getTopHeadlines(country)
            emit(BaseNetworkResult.Loading(false))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Success(response.body()!!))
            } else {
                emit(BaseNetworkResult.Error("Xatolik"))
            }
        }
    }

    override suspend fun getSearchedArticles(query: String): Flow<BaseNetworkResult<TopHeadlines>> {
        return flow {
            emit(BaseNetworkResult.Loading(true))
            val response =
                service.getSearchedArticles(q = query)
            emit(BaseNetworkResult.Loading(false))
            if (response.code() == 200) {
                emit(BaseNetworkResult.Success(response.body()!!))
            } else {
                emit(BaseNetworkResult.Error("Xatolik"))
            }
        }
    }
}