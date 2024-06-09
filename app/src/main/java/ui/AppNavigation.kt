package ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import viewmodel.BlogViewModel

@Composable
fun AppNavigation(viewModel: BlogViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "blogList") {
        composable("blogList") {
            BlogListScreen(navController, viewModel)
        }
        composable("blogDetail/{blogId}") { backStackEntry ->
            val blogId = backStackEntry.arguments?.getString("blogId")?.toInt() ?: return@composable
            BlogDetailScreen(blogId, viewModel)
        }
    }
}