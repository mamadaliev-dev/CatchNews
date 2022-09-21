package uz.madgeeks.catchnews.presentation.home

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.madgeeks.catchnews.R
import uz.madgeeks.catchnews.data.base.SharedPrefrenceHelper
import uz.madgeeks.catchnews.databinding.FragmentHomeBinding
import uz.madgeeks.catchnews.presentation.BaseFragment
import uz.madgeeks.catchnews.presentation.adapters.NewsAdapter
import uz.madgeeks.catchnews.presentation.base.BaseViewModel


@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: BaseViewModel by viewModels()
    private val adapter by lazy {
        NewsAdapter()
    }
    private val shared by lazy {
        SharedPrefrenceHelper(requireContext())
    }

    override fun onViewCreate() {

        binding.list.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false)
        binding.list.adapter = adapter

        var countryCodes = arrayOf("us", "ru",
            "gb", "fr",
            "tr", "jp")

        binding.spinner.setItems("United States\uD83C\uDDFA\uD83C\uDDF8",
            "Russia\uD83C\uDDF7\uD83C\uDDFA",
            "Great Britain\uD83C\uDDEC\uD83C\uDDE7",
            "France\uD83C\uDDEB\uD83C\uDDF7",
            "Turkey\uD83C\uDDF9\uD83C\uDDF7",
            "Japan\uD83C\uDDEF\uD83C\uDDF5")

        binding.spinner.selectedIndex = shared.getPosition()
        binding.spinner.setOnItemSelectedListener { view, position, id, item ->
            shared.setPosition(position)
            viewModel.getLatestHeadlines(countryCodes[position])
        }

        adapter.setItemClickListener { title, author, url, description, urlToImage, publishedAt ->
            val bundle = bundleOf("TITLE" to title,
                "AUTHOR" to author,
                "URL" to url,
                "DESCRIPTION" to description,
                "URLTOIMAGE" to urlToImage,
                "PUBLISHEDAT" to publishedAt)
            navController.navigate(R.id.action_navigation_home_to_newsInfoFragment, bundle)
        }

        viewModel.latestHeadlinesLiveData.observe(viewLifecycleOwner) {
            adapter.setNews(it.articles)
        }

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getLatestHeadlines(countryCodes[shared.getPosition()])
        }

        viewModel.getLatestHeadlines(countryCodes[shared.getPosition()])
    }
}