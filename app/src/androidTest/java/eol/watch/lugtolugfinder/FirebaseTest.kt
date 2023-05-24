package eol.watch.lugtolugfinder

import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG
import com.google.firebase.database.*
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class FirebaseTest {

    @Test
    fun writeAndReadUserToDatabase() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("users")

        val user = User("username", "email@example.com")
        myRef.setValue(user)

        // Create a latch that will be released when data is read from Firebase
        val latch = CountDownLatch(1)

        // Attach a listener to read the data at our posts reference
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userFromDb = dataSnapshot.getValue(User::class.java)
                assertEquals(user, userFromDb)

                // Release the latch
                latch.countDown()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })

        // Wait for the latch to be released
        latch.await(5, TimeUnit.SECONDS)
    }
}

data class User(
    var username: String = "",
    var email: String = ""
)


