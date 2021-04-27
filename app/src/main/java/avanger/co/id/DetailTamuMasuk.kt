package avanger.co.id

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_daftar_tamu.*
import java.util.*
import kotlin.concurrent.schedule

class DetailTamuMasuk : AppCompatActivity() {
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
            detailTamuJamMasuk.text = Utilities.unixToTime(tamu.jamMasuk)

            // Lazy-load image into the imageView.
            Picasso.get().load(tamu.photo).resize(1000, 800).into(fotoTamu)
        }

        // Delegate listeners.
        btnKembali.setOnClickListener { finish() }
        btnSubmit.setOnClickListener {
            tamu?.idTamu?.let { id ->
                val database = Firebase.firestore
                val currentItem =  database.collection(getString(R.string.firebase_document)).document(id)
                val updatedData = hashMapOf<String, Any>(
                        "jamKeluar" to System.currentTimeMillis() / 1000L,
                        "didalamKompleks" to false
                )

                currentItem.update(updatedData).addOnSuccessListener {
                    Snackbar.make(detailDaftarTamu, getString(R.string.detail_tamu_success), Snackbar.LENGTH_LONG).show()

                    Timer().schedule(1000) {
                        finish()
                    }
                }.addOnFailureListener {
                    Snackbar.make(detailDaftarTamu, getString(R.string.detail_tamu_failed), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}
