package eol.watch.lugtolugfinder

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class BottomNavigationItem(val route: String, val icon: ImageVector) {
    Search("search", Icons.Default.Search),
    Watch("watch", Icons.Default.Watch),
    MyPage("mypage", Icons.Default.Person)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val colors = if (isSystemInDarkTheme()) {
        Log.d("checkDarkMode","다크 모드 확인...")
        darkColors(
            primary = Color.Gray,
            onPrimary = Color.White,
            background = Color.Black,
            onBackground = Color.White
        )
    } else {
        lightColors(
            primary = Color.White,
            onPrimary = Color.Black,
            background = Color.White,
            onBackground = Color.Black
        )
    }

    val items = listOf(
        BottomNavigationItem.Search,
        BottomNavigationItem.Watch,
        BottomNavigationItem.MyPage
    )
    var selectedItem by remember { mutableStateOf(BottomNavigationItem.Search) }

    val navController = rememberNavController()

    MaterialTheme(colors = colors) {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                bottomBar = {
                    BottomNavigation {
                        items.forEach { item ->
                            BottomNavigationItem(
                                icon = { Icon(item.icon, contentDescription = null) },
                                selected = selectedItem == item,
                                onClick = {
                                    selectedItem = item
                                    navController.navigate(item.route)
                                }
                            )
                        }
                    }
                }
            ) { paddingValues ->
                Box(Modifier.padding(paddingValues)) {
                    NavHost(navController, startDestination = BottomNavigationItem.Search.route) {
                        composable(BottomNavigationItem.Search.route) { SearchScreen() }
                        composable(BottomNavigationItem.Watch.route) { /* Watch Screen Composable */ }
                        composable(BottomNavigationItem.MyPage.route) { /* MyPage Screen Composable */ }
                    }
                }
            }
        }
    }
}
