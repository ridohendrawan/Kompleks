package avanger.co.id

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import avanger.co.id.databinding.ActivityDetailTamuKeluarBinding
import com.squareup.picasso.Picasso

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

//            if (tamu.didalamKompleks == true) {
//                binding.detailStatusTamu.text = getString(R.string.detail_tamu_status_nope)
//            } else {
//                binding.detailStatusTamu.text = getString(R.string.detail_tamu_status_done)
//            }

            // Lazy-load image into the imageView.
            Picasso.get().load(tamu.photo).resize(1000, 800).into(binding.fotoTamu)
        }

        // Delegate listeners.
        binding.btnKembali.setOnClickListener { finish() }
    }
}
