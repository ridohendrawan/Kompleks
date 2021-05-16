package avanger.co.id

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import avanger.co.id.databinding.ActivityFormBaruBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import id.zelory.compressor.constraint.destination
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.concurrent.schedule

class FormBaru : AppCompatActivity() {
    companion object {
        private const val cameraRequestCode = 1
    }

    private var imagePath: String = ""
    private lateinit var binding: ActivityFormBaruBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBaruBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Delegasi listeners.
        binding.btnKembali.setOnClickListener { finish() }
        binding.btnFoto.setOnClickListener { prosesKamera() }
        binding.btnSubmit.setOnClickListener { handleSubmission() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == cameraRequestCode && resultCode == RESULT_OK) {
            val gambar = File(imagePath)

            gambar.let {
                lifecycleScope.launch {
                    Compressor.compress(applicationContext, it) {
                        default(quality = 10, format = Bitmap.CompressFormat.JPEG)
                        destination(gambar)
                    }.let {
                        binding.placeholderGambar.setImageBitmap(BitmapFactory.decodeFile(it.absolutePath))
                    }
                }
            }
        } else {
            binding.placeholderGambar.setImageDrawable(null)
        }
    }

    /**
     * Fungsi akan melakukan pemrosesan kamera.
     * Ketika gambar selesai didapatkan, gambar akan dikompres.
     */
    private fun prosesKamera() {
        val intentAmbilFoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val fileKosong = membuatFile()

        fileKosong?.let {
            FileProvider.getUriForFile(
                    applicationContext,
                    getString(R.string.authority),
                    it
            ).also { uri ->
                intentAmbilFoto.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                startActivityForResult(intentAmbilFoto, cameraRequestCode)
            }
        }
    }

    /**
     * Fungsi bertugas untuk membuat file kosong dengan filename berupa UNIX timestamp.
     * Akan mengembalikan file tersebut, atau null jika error.
     */
    @Throws(IOException::class)
    private fun membuatFile(): File? {
        try {
            val timestamp = (System.currentTimeMillis() / 1000L).toString()
            val direktoriPenyimpanan = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val gambar = File.createTempFile(timestamp, ".jpg", direktoriPenyimpanan)

            imagePath = gambar.absolutePath
            return gambar
        } catch (e: IOException) {
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
        }

        return null
    }

    /**
     * Fungsi untuk melakukan submission handling.
     */
    private fun handleSubmission() {
        // Prepare database.
        val db = Firebase.firestore
        val storage = Firebase.storage

        // Parse input.
        val nama = binding.namaTamu.text.toString()
        val tujuan = binding.tujuanTamu.text.toString()
        val plat = binding.platTamu.text.toString().toUpperCase(Locale.ROOT)
        val jamMasuk = (System.currentTimeMillis() / 1000)
        val jamKeluar = 0L

        if (nama.isBlank() || tujuan.isBlank() || plat.isBlank() || binding.placeholderGambar.drawable == null) {
            return Snackbar.make(binding.formBaru, getString(R.string.required_fields), Snackbar.LENGTH_LONG).show()
        }

        val gambarCloud = Uri.fromFile(File(imagePath))
        val ref = storage.reference.child(getString(R.string.firebase_storage)).child(jamMasuk.toString())

        ref.putFile(gambarCloud).addOnProgressListener {
            binding.formProgress.visibility = View.VISIBLE
        }.addOnFailureListener {
            binding.formProgress.visibility = View.GONE
            Toast.makeText(this, getString(R.string.internal_error), Toast.LENGTH_LONG).show()
        }.addOnSuccessListener { res ->
            res.let {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    val uuid = UUID.randomUUID().toString()
                    val tamu = Tamu(uuid, nama, tujuan, plat, jamMasuk, jamKeluar, uri.toString(), true)
                    val tamuRef = db.collection(getString(R.string.firebase_document)).document(uuid)

                    tamuRef.set(tamu).addOnSuccessListener {
                        Snackbar.make(binding.formBaru, getString(R.string.success_upload), Snackbar.LENGTH_LONG).show()

                        binding.formProgress.visibility = View.GONE
                        File(imagePath).delete()

                        Timer().schedule(500) {
                            finish()
                        }
                    }.addOnFailureListener {
                        binding.formProgress.visibility = View.GONE
                        Toast.makeText(this, getString(R.string.internal_error_rdb), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}