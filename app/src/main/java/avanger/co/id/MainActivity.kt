package avanger.co.id

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Firebase Authentication
        auth = Firebase.auth

        lupapassword.setOnClickListener { openLupaPassword() }

        login.setOnClickListener {
            val username = username.text.toString()
            val password = password.text.toString()
            val usernameSuffix = getString(R.string.firebase_email)
            val firebaseUsername = "$username@$usernameSuffix"

            // Ketika seorang user berhasil login, tidak perlu apa-apa lagi karena sudah berada di Firebase.
            // Segi security, sangat aman - Firebase does all of the abstractions.
            auth.signInWithEmailAndPassword(firebaseUsername, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    openMainMenu()
                } else {
                    Snackbar.make(activity_main_view, getString(R.string.main_fail_auth), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun openLupaPassword() {
        val intent = Intent(this@MainActivity, LupaPassword::class.java)
        startActivity(intent)
    }

    private fun openMainMenu() {
        val intent = Intent(this@MainActivity, MainMenu::class.java)
        startActivity(intent)
        finish()
    }
}