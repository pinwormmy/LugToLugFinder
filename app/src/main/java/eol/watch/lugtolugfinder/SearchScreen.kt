package eol.watch.lugtolugfinder

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import eol.watch.lugtolugfinder.model.Watch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Composable
fun SearchScreen() {
    var searchText by remember { mutableStateOf("") }
    val searchResults = remember { mutableStateOf(listOf<Watch>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = if (searchResults.value.isEmpty() && searchText.isBlank()) Arrangement.Center else Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (searchResults.value.isEmpty() && searchText.isBlank()) {
                Text(
                    text = "Lug to Lug Finder",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface
                )
            } else {
                searchResults.value.forEach { watch ->
                    Text(text = "${watch.brand} - ${watch.name}", style = MaterialTheme.typography.body1)
                }
            }
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { newValue ->
                    searchText = newValue
                },
                label = { Text("검색어를 입력하세요") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    LocalSoftwareKeyboardController.current?.hide()
                })
            )1
        }
    }

    LaunchedEffect(searchText) {
        searchResults.value = searchWatchesInDatabase(searchText)
    }
}

suspend fun searchWatchesInDatabase(searchText: String): List<Watch> = withContext(Dispatchers.IO) {
    if (searchText.isBlank()) {
        return@withContext emptyList<Watch>()
    }

    val database = FirebaseDatabase.getInstance("https://lug-to-lug-finder-default-rtdb.asia-southeast1.firebasedatabase.app")
    val myRef = database.getReference("watches")

    val snapshot = myRef.get().await()
    return@withContext snapshot.children.mapNotNull { it.getValue(Watch::class.java) }
        .filter { it.brand.contains(searchText, ignoreCase = true) ||
                it.name.contains(searchText, ignoreCase = true) ||
                it.productNumber.contains(searchText, ignoreCase = true) }
}

suspend fun <T> Task<T>.await(): T = suspendCoroutine { continuation ->
    addOnSuccessListener { result ->
        Log.d("Firebase", "Task succeeded with result: $result")
        continuation.resume(result)
    }
    addOnFailureListener { exception ->
        Log.e("Firebase", "Task failed with exception", exception)
        continuation.resumeWithException(exception)
    }
}
