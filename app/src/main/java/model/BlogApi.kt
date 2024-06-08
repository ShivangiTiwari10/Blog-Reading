package model

import retrofit2.http.GET

interface BlogApi {


   @GET ("wp-json/wp/v2/posts?per_page=10&page=1")
   suspend fun getBlogPosts(): List<>


}