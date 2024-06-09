package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import model.BlogPostItem
import viewmodel.BlogViewModel

@Composable
fun BlogListScreen(navController: NavHostController, viewModel: BlogViewModel) {
    val blogPosts by viewModel.blogPosts.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column {
        Text(
            text = "Blog Reading App",
            color = Color.Blue,
            fontFamily = FontFamily.Monospace,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)

        )
        LazyColumn (state = listState){
            items(blogPosts) { blog ->
                BlogListItem(blog) {
                    navController.navigate("blogDetail/${blog.id}")
                }
            }
            item {
                if (listState.isScrolledToEnd()) {
                    coroutineScope.launch {
                        viewModel.fetchBlogPosts()
                    }
                }
            }
        }


    }
}@Composable
fun LazyListState.isScrolledToEnd(): Boolean {
    val visibleItemsInfo = layoutInfo.visibleItemsInfo
    return if (visibleItemsInfo.isNotEmpty()) {
        val lastVisibleItemIndex = visibleItemsInfo.last().index
        val totalItemsCount = layoutInfo.totalItemsCount
        lastVisibleItemIndex == totalItemsCount - 1
    } else {
        false
    }
}



@Composable
fun BlogListItem(blog: BlogPostItem, onClick: () -> Unit) {


    Card (modifier = Modifier.padding(8.dp)){

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(onClick = onClick)
        ) {
            Text(text = blog.title.rendered, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = blog.content.rendered,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )

        }
    }

}