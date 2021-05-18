package avanger.co.id

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import avanger.co.id.databinding.ActivityDetailTamuMasukBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.util.*
import kotlin.concurrent.schedule

class DetailTamuMasuk : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTamuMasukBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTamuMasukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from the previous activity.
        val tamu: Tamu? = intent.getParcelableExtra("tamu")

        // Let's just set some text to show results.
        if (tamu != null) {
            binding.detailTamuName.text = tamu.namaTamu
            binding.detailTamuTujuan.text = tamu.tujuanKunjungan
            binding.detailTamuPlat.text = tamu.platTamu
            binding.detailTamuJamMasuk.text = Utilities.unixToTime(tamu.jamMasuk)

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
        binding.btnSubmit.setOnClickListener {
            tamu?.idTamu?.let { id ->
                binding.formProgress.visibility = View.VISIBLE

                val database = Firebase.firestore
                val currentItem = database.collection(getString(R.string.firebase_document)).document(id)
                val updatedData = hashMapOf<String, Any>(
                        "jamKeluar" to System.currentTimeMillis() / 1000L,
                        "didalamKompleks" to false
                )

                currentItem.update(updatedData).addOnSuccessListener {
                    binding.formProgress.visibility = View.GONE
                    Snackbar.make(binding.detailDaftarTamu, getString(R.string.detail_tamu_success), Snackbar.LENGTH_LONG).show()

                    Timer().schedule(200) {
                        finish()
                    }
                }.addOnFailureListener {
                    binding.formProgress.visibility = View.GONE
                    Snackbar.make(binding.detailDaftarTamu, getString(R.string.detail_tamu_failed), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}
