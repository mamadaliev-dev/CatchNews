package uz.madgeeks.catchnews.presentation.search

import android.view.View
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.madgeeks.catchnews.R
import uz.madgeeks.catchnews.databinding.FragmentSearchBinding
import uz.madgeeks.catchnews.presentation.BaseFragment
import uz.madgeeks.catchnews.presentation.adapters.NewsSearchAdapter
import uz.madgeeks.catchnews.presentation.base.BaseViewModel


@AndroidEntryPoint
class SearchFragment :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    val viewModel: BaseViewModel by viewModels()
    val adapter by lazy {
        NewsSearchAdapter()
    }

    override fun onViewCreate() {
        binding.searchedList.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false)
        binding.searchedList.adapter = adapter

        adapter.setItemClickListener { title, author, url, description, urlToImage, publishedAt ->
            val bundle = bundleOf("TITLE" to title,
                "AUTHOR" to author,
                "URL" to url,
                "DESCRIPTION" to description,
                "URLTOIMAGE" to urlToImage,
                "PUBLISHEDAT" to publishedAt)
            navController.navigate(R.id.action_navigation_search_to_newsInfoFragment, bundle)
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    binding.notFoundLayout.visibility = View.VISIBLE
                    binding.searchedList.visibility = View.GONE
                } else {
                    viewModel.getSearchedArticles(query = query)
                    viewModel.searchedArticlesLiveData.observe(viewLifecycleOwner) {
                        if (it.isEmpty()) {
                            binding.notFoundLayout.visibility = View.VISIBLE
                            binding.searchedList.visibility = View.GONE
                        } else {
                            binding.notFoundLayout.visibility = View.GONE
                            binding.searchedList.visibility = View.VISIBLE
                            adapter.setArticles(it)
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.getSearchedArticles(query = it) }
                viewModel.searchedArticlesLiveData.observe(viewLifecycleOwner) {
                    if (it.isEmpty()) {
                        binding.notFoundLayout.visibility = View.VISIBLE
                        binding.searchedList.visibility = View.GONE
                    } else {
                        binding.notFoundLayout.visibility = View.GONE
                        binding.searchedList.visibility = View.VISIBLE
                        adapter.setArticles(it)
                    }
                }
                return false
            }
        })
    }
}