package viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.BlogPostItem

class BlogViewModel : ViewModel() {

    private val _blogPosts = MutableStateFlow<List<BlogPostItem>>(emptyList())
    val blogPosts: StateFlow<List<BlogPostItem>> = _blogPosts

    init {
        fetchBlogPosts()
    }

    private fun fetchBlogPosts() {


        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getBlogPosts()
                if (response.isSuccessful) {
                    _blogPosts.value = response.body() ?: emptyList()
                    Log.d("BlogViewModel1", "Blog posts loaded: ${_blogPosts.value}")
                } else {
                    Log.e("BlogViewModel2", "Failed to fetch blog posts: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("BlogViewModel3", "Exception: ${e.message}")
            }

        }
    }
}