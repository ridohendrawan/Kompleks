package avanger.co.id

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import avanger.co.id.databinding.ActivityLupaPasswordBinding

class LupaPassword : AppCompatActivity() {
    private lateinit var binding: ActivityLupaPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.KembaliMain.setOnClickListener {
            finish()
        }
    }
}