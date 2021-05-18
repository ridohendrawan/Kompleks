package avanger.co.id

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
    companion object {
        private const val CHANNEL_ID = "1"
        private const val NOTIFICATION_ID = 1
    }

    override suspend fun doWork(): Result {
        if (Utilities.getCurrentDay() != Calendar.MONDAY) {
            return Result.success()
        }

        val db = Firebase.firestore
        val storage = Firebase.storage
        val currentTime = System.currentTimeMillis()
        val timeInterval = TimeUnit.MILLISECONDS.convert(30, TimeUnit.DAYS)
        val cutoff = (currentTime - timeInterval) / 1000L

        return try {
            val outdatedData = db
                    .collection(applicationContext.getString(R.string.firebase_document))
                    .whereNotEqualTo("jamKeluar", 0)
                    .whereLessThan("jamKeluar", cutoff)
                    .orderBy("jamKeluar")
                    .get()
                    .await()

            outdatedData?.forEach { snapshot ->
                val storageRef = storage
                        .getReference(applicationContext.getString(R.string.firebase_storage))
                        .child(snapshot.get("jamMasuk").toString())

                storageRef.delete().await()
                snapshot.reference.delete().await()

                createNotification()
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun createNotification() {
        val intent = Intent(applicationContext, MainMenu::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(applicationContext.getString(R.string.notification_title))
                .setContentText(applicationContext.getString(R.string.notification_body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Kompleks Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
            val notificationManager = applicationContext
                    .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(notificationChannel)
        }

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(NOTIFICATION_ID, notification.build())
        }
    }
}