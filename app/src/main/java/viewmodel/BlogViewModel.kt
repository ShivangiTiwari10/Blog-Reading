package viewmodel

import BlogRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.BlogPostItem

class BlogViewModel : ViewModel() {

    private val repository = BlogRepository()
    private val _blogPosts = MutableStateFlow<List<BlogPostItem>>(emptyList())
    val blogPosts: StateFlow<List<BlogPostItem>> = _blogPosts

    private var currentPage = 1

    //    ***
    private var isLoading = false

    init {
        fetchBlogPosts()
    }

    fun fetchBlogPosts() {
        viewModelScope.launch {
            if (!isLoading) {
                isLoading = true
                try {
                    val response = repository.getBlogPosts(page = currentPage)
                    if (response.isSuccessful) {
                        val newPosts = response.body() ?: emptyList()
                        _blogPosts.value += newPosts
                        currentPage++
                        Log.d("BlogViewModel", "Blog posts loaded: ${_blogPosts.value}")
                    } else {
                        Log.e(
                            "BlogViewModel",
                            "Failed to fetch blog posts: ${response.errorBody()}"
                        )
                    }
                } catch (e: Exception) {
                    Log.e("BlogViewModel", "Exception: ${e.message}")
                }
                isLoading = false
            }
        }

    }
}