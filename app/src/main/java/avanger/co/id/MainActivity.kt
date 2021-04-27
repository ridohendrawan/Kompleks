package avanger.co.id

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import avanger.co.id.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase Authentication
        auth = Firebase.auth

        binding.lupapassword.setOnClickListener { openLupaPassword() }

        binding.login.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            val usernameSuffix = getString(R.string.firebase_email)
            val firebaseUsername = "$username@$usernameSuffix"

            // Tampilkan progress bar.
            binding.formProgress.visibility = View.VISIBLE

            // Ketika seorang user berhasil login, tidak perlu apa-apa lagi karena sudah berada di Firebase.
            // Segi security, sangat aman - Firebase does all of the abstractions.
            auth.signInWithEmailAndPassword(firebaseUsername, password).addOnCompleteListener { task ->
                binding.formProgress.visibility = View.GONE

                if (task.isSuccessful) {
                    openMainMenu()
                } else {
                    Snackbar.make(binding.activityMainView, getString(R.string.main_fail_auth), Snackbar.LENGTH_LONG).show()
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