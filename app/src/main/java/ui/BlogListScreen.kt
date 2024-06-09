package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import model.BlogPostItem
import viewmodel.BlogViewModel

@Composable
fun BlogListScreen(navController: NavHostController, viewModel: BlogViewModel) {
    val blogPosts by viewModel.blogPosts.collectAsState()

    LazyColumn {
        items(blogPosts) { blog ->
            BlogListItem(blog) {
                navController.navigate("blogDetail/${blog.id}")
            }
        }
    }
}
@Composable
fun BlogListItem(blog: BlogPostItem, onClick: () -> Unit) {
    Card (modifier = Modifier.padding(5.dp)){
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