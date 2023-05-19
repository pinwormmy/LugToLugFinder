package eol.watch.lugtolugfinder

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Watch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

enum class BottomNavigationItem(val route: String, val icon: ImageVector, val title: String) {
    Search("search", Icons.Default.Search, "검색"),
    Watch("watch", Icons.Default.Watch, "시계 보기"),
    MyPage("mypage", Icons.Default.Person, "마이페이지")
}


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                Greeting("머티리얼 디자인3!")
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
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

    Scaffold(
        bottomBar = {
            BottomNavigation {
                items.forEach { item ->
                    BottomNavigationItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.title) },
                        selected = selectedItem == item,
                        onClick = { selectedItem = item }
                    )
                }
            }
        }
    ) {
        //
    }

    MaterialTheme(colors = colors) {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "안뇽~ $name!") },
                actions = {
                    IconButton(onClick = { /* TODO: Handle click */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Extended FAB") },
                onClick = { /* TODO: Handle click */ }
            )
        }
    ) { innerPadding ->
        BodyContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "Hello World!")
        TextButton(onClick = { /* TODO: Handle click */ }) {
            Text("Text Button")
        }
        Button(onClick = { /* TODO: Handle click */ }) {
            Text("Button")
        }
        Card(
            modifier = Modifier.padding(top = 8.dp),
            elevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "카드형 공간입니다..")
                TextField(
                    value = "",
                    onValueChange = { /* TODO: Handle text change */ },
                    label = { Text("입력하시오") },
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        Greeting("디폴트프리뷰")
    }
}

