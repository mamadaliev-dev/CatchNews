package uz.madgeeks.catchnews.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.madgeeks.catchnews.data.base.getFormatDate
import uz.madgeeks.catchnews.data.home.response.Article
import uz.madgeeks.catchnews.databinding.ItemNewsSearchBinding

class NewsSearchAdapter : RecyclerView.Adapter<NewsSearchAdapter.MovieCardViewHolder>() {
    var data = mutableListOf<Article>()

    private var itemClickListener: ((title: String, author: String, url: String, description: String, urlToImage: String, publishedAt: String) -> Unit)? =
        null

    fun setItemClickListener(f: (title: String, author: String, url: String, description: String, urlToImage: String, publishedAt: String) -> Unit) {
        itemClickListener = f
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setArticles(nData: List<Article>) {
        this.data.clear()
        this.data.addAll(nData)
        notifyDataSetChanged()
    }

    inner class MovieCardViewHolder(private val binding: ItemNewsSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: Article) {
            binding.apply {
                title.text = data.title
                time.text = data.publishedAt.getFormatDate()
            }

            Glide.with(binding.root.context)
                .load(data.urlToImage)
                .into(binding.image)

            itemView.setOnClickListener {
                data.author?.let { it1 ->
                    itemClickListener?.invoke(data.title,
                        it1,
                        data.url,
                        data.description,
                        data.urlToImage,
                        data.publishedAt)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieCardViewHolder(
        ItemNewsSearchBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) =
        holder.bindData(data[position])
}