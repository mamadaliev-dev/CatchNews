package uz.madgeeks.catchnews.data.base

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import uz.madgeeks.catchnews.BuildConfig
import uz.madgeeks.catchnews.data.home.response.TopHeadlines

interface BaseService {
    @GET("/v2/top-headlines")
    @Headers("x-api-key: ${BuildConfig.TOKEN}")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
    ): Response<TopHeadlines>

    @GET("everything")
    @Headers("x-api-key: ${BuildConfig.TOKEN}")
    suspend fun getSearchedArticles(
        @Query("q") q: String,
        @Query("sortBy") sortBy: String = "popularity",
    ): Response<TopHeadlines>
}