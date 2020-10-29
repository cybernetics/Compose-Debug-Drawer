package com.alorma.composedrawer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.ui.tooling.preview.Preview
import com.alorma.composedrawer.ui.ComposeDrawerTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val host = rememberNavController()
            NavHost(
                navController = host,
                startDestination = "home",
            ) {
                composable(route = "home") { HomeScreen(host = host) }
                composable(route = "profile") { ProfileScreen(host = host) }
                composable(route = "detail/{detailId}",
                    arguments = listOf(
                        navArgument("detailId") {
                            type = NavType.StringType
                        }
                    )
                ) { backStackEntry ->
                    val detailId = backStackEntry.arguments?.getString("detailId")
                    DetailScreen(host = host, detailId = detailId)
                }
            }
            ComposeDrawerTheme {
                HomeScreen(host)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeDrawerTheme {
        HomeScreen(rememberNavController())
    }
}