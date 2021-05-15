package avanger.co.id

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import avanger.co.id.databinding.ActivityPanduanPenggunaBinding

class PanduanPengguna : AppCompatActivity() {
    private lateinit var binding: ActivityPanduanPenggunaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanduanPenggunaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.KembaliMenu.setOnClickListener { finish() }
    }
}