package uz.madgeeks.catchnews.data.home.response

data class TopHeadlines(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)