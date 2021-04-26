package avanger.co.id

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_tamu_keluar.*

class DetailTamuKeluar : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tamu_keluar)

        // Get data from the previous activity.
        val tamu: Tamu? = intent.getParcelableExtra("tamu")

        // Let's just set some text to show results.
        if (tamu != null) {
            detailTamuName.text = tamu.namaTamu
            detailTamuTujuan.text = tamu.tujuanKunjungan
            detailTamuPlat.text = tamu.platTamu
            detailTamuJamMasuk.text = Utilities.unixToTime(tamu.jamMasuk)
            detailTamuJamKeluar.text = Utilities.unixToTime(tamu.jamKeluar)

            if (tamu.didalamKompleks == true) {
                detailStatusTamu.text = getString(R.string.detail_tamu_status_nope)
            } else {
                detailStatusTamu.text = getString(R.string.detail_tamu_status_done)
            }

            // Lazy-load image into the imageView.
            Picasso.get().load(tamu.photo).resize(1000, 800).into(fotoTamu)

            // Setup Firebase.
            database = FirebaseUtils.getFirebaseInstance().reference
        }

        // Delegate listeners.
        btnKembali.setOnClickListener { finish() }
    }
}
