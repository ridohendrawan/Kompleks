package avanger.co.id

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.concurrent.schedule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hilangkan ActionBar.
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash_screen)

        // Do all Firebase stuff in a separate coroutine.
        Timer().schedule(200) {
            CoroutineScope(Dispatchers.IO).launch {
                auth = Firebase.auth

                if (auth.currentUser != null) {
                    openMainMenu()
                } else {
                    openLoginMenu()
                }
            }
        }
    }

    private fun openLoginMenu() {
        startActivity(Intent(this@SplashScreen, MainActivity::class.java))
        finish()
    }

    private fun openMainMenu() {
        startActivity(Intent(this@SplashScreen, MainMenu::class.java))
        finish()
    }
}