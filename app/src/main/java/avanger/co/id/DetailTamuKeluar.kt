package avanger.co.id

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import avanger.co.id.databinding.ActivityDetailTamuKeluarBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class DetailTamuKeluar : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTamuKeluarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTamuKeluarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from the previous activity.
        val tamu: Tamu? = intent.getParcelableExtra("tamu")

        // Let's just set some text to show results.
        if (tamu != null) {
            binding.detailTamuName.text = tamu.namaTamu
            binding.detailTamuTujuan.text = tamu.tujuanKunjungan
            binding.detailTamuPlat.text = tamu.platTamu
            binding.detailTamuJamMasuk.text = Utilities.unixToTime(tamu.jamMasuk)
            binding.detailTamuJamKeluar.text = Utilities.unixToTime(tamu.jamKeluar)

            // Lazy-load image into the imageView.
            Picasso.get()
                    .load(tamu.photo)
                    .resize(1000, 800)
                    .into(binding.fotoTamu, object : Callback {
                        override fun onError(e: Exception?) {
                            binding.fotoTamuErrorText.visibility = View.VISIBLE
                        }

                        override fun onSuccess() {
                            binding.fotoTamu.visibility = View.VISIBLE
                        }
                    })
        }

        // Delegate listeners.
        binding.btnKembali.setOnClickListener { finish() }
    }
}
