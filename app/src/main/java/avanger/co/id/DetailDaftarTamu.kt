package avanger.co.id

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_detail_daftar_tamu.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.schedule

class DetailDaftarTamu : AppCompatActivity() {
    private lateinit var database: DatabaseReference

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

            // Setup Firebase.
            database = FirebaseDatabase.getInstance().reference
        }

        // Delegate listeners.
        btnKembali.setOnClickListener { finish() }
        btnSubmit.setOnClickListener {
            tamu?.idTamu?.let { id ->
                val currentItem = database.child(getString(R.string.firebase_document))
                        .child(getString(R.string.firebase_document))
                        .child(id)

                val updatedData = HashMap<String, Any>()
                updatedData["jamKeluar"] = System.currentTimeMillis() / 1000L
                updatedData["didalamKompleks"] = false;

                currentItem.updateChildren(updatedData).addOnCompleteListener {
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
