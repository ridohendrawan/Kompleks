package avanger.co.id

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit

class DeleteTask(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        if (Utilities.getCurrentDay() != Calendar.SUNDAY) {
            return Result.success()
        }

        val db = Firebase.firestore
        val storage = Firebase.storage
        val currentTime = System.currentTimeMillis()
        val timeInterval = TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES)
        val cutoff = (currentTime - timeInterval) / 1000L

        return try {
            val outdatedData = db
                    .collection("tamu")
                    .whereNotEqualTo("jamKeluar", 0)
                    .orderBy("jamKeluar")
                    .whereLessThan("jamKeluar", cutoff)
                    .get()
                    .await()

            outdatedData.forEach { snapshot ->
                val storageRef = storage
                        .getReference("foto_tamu")
                        .child(snapshot.get("jamMasuk").toString())

                storageRef.delete().await()
                snapshot.reference.delete().await()
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}