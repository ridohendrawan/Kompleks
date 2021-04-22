package avanger.co.id

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail_daftar_tamu.*

class DetailDaftarTamu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_daftar_tamu)

        // Get data from the previous activity.
        val tamu: Tamu? = intent.getParcelableExtra("tamu")

        // Let's just set some text to show results.
        if (tamu != null) {
            detailTamuName.text = tamu.namaTamu
            detailTamuTujuan.text = tamu.tujuanKunjungan
            detailTamuPlat.text = tamu.platTamu
            detailTamuJamMasuk.text = tamu.jamMasuk.toString()
            detailTamuJamKeluar.text = tamu.jamKeluar.toString()
        }

        // Delegate listeners.
        btnKembali.setOnClickListener { finish() }
    }
}