package Repository

import data.RetrofitInstance
import model.BlogPostItem
import retrofit2.Response

class BlogRepository {

    private val api = RetrofitInstance.api

    suspend fun getBlogPosts(): Response<List<BlogPostItem>> {
        return api.getBlogPosts()
    }
}