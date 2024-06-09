package ui

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import viewmodel.BlogViewModel


@Composable
fun BlogDetailScreen(blogId: Int, viewModel: BlogViewModel) {
    Log.d("BlogDetailScreen", "Displaying blog with ID: $blogId")
    val blogPost = viewModel.blogPosts.value.find { it.id == blogId }


    if (blogPost != null) {
        WebViewContent(blogPost.content.rendered)
    } else {
        Log.e("BlogDetailScreen", "Blog post not found for ID: $blogId")
    }

}

@Composable
fun WebViewContent(content: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadDataWithBaseURL(null, content, "text/html", "UTF-8", null)
            }
        },
        update = { webView ->
            webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null)
        },
        modifier = Modifier.background(Color.LightGray)
    )


}