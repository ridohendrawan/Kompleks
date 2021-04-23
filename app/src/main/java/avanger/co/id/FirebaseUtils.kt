package avanger.co.id

import com.google.firebase.database.FirebaseDatabase

class FirebaseUtils {
    companion object {
        private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

        init {
            database.setPersistenceEnabled(true)
        }

        fun getFirebaseInstance(): FirebaseDatabase {
            return database
        }
    }
}