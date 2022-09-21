package uz.madgeeks.catchnews.presentation.news_info

import android.content.Intent
import android.net.Uri
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.madgeeks.catchnews.data.base.getFormatDate
import uz.madgeeks.catchnews.data.base.getTime
import uz.madgeeks.catchnews.databinding.FragmentNewsInfoBinding
import uz.madgeeks.catchnews.presentation.BaseFragment


@AndroidEntryPoint
class NewsInfoFragment : BaseFragment<FragmentNewsInfoBinding>(FragmentNewsInfoBinding::inflate) {
    override fun onViewCreate() {
        val publishedAt = requireArguments().getString("PUBLISHEDAT", "Error")
        val URL = requireArguments().getString("URL", "Error")

        binding.apply {
            title.text = requireArguments().getString("TITLE", "Error")
            description.text = requireArguments().getString("DESCRIPTION", "Error")
            publishedTime.text = publishedAt.getTime()
            publishedDate.text = publishedAt.getFormatDate()
        }

        Glide.with(binding.root.context)
            .load(requireArguments().getString("URLTOIMAGE", "Error"))
            .into(binding.image)

        binding.btnReadMore.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(URL))
            startActivity(browserIntent)
        }
    }
}