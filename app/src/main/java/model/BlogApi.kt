package model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface BlogApi {
   @GET("wp-json/wp/v2/posts")
   suspend fun getBlogPosts(
      @Query("per_page") perPage: Int = 10,
      @Query("page") page: Int
   ): Response<List<BlogPostItem>>
}
