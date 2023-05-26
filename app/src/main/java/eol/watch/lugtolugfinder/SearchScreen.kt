package eol.watch.lugtolugfinder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display search results
        searchResults.value.forEach { watch ->
            // TODO: Display each watch
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = searchText,
            onValueChange = { newValue ->
                searchText = newValue
            },
            label = { Text("검색어를 입력하세요") },
            modifier = Modifier.fillMaxWidth()
        )
        // Trigger search when searchText changes
        LaunchedEffect(searchText) {
            searchResults.value = searchWatchesInDatabase(searchText)
        }
    }
}

suspend fun searchWatchesInDatabase(searchText: String): List<Watch> = withContext(Dispatchers.IO) {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("watches")

    val snapshot = myRef.get().await()
    return@withContext snapshot.children.mapNotNull { it.getValue(Watch::class.java) }
        .filter { it.brand.contains(searchText, ignoreCase = true) ||
                it.name.contains(searchText, ignoreCase = true) ||
                it.productNumber.contains(searchText, ignoreCase = true) }
}

suspend fun <T> Task<T>.await(): T = suspendCoroutine { continuation ->
    addOnSuccessListener { result ->
        continuation.resume(result)
    }
    addOnFailureListener { exception ->
        continuation.resumeWithException(exception)
    }
}
