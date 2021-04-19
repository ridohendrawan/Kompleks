package avanger.co.id

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import id.zelory.compressor.constraint.destination
import kotlinx.android.synthetic.main.activity_form_baru.*
import kotlinx.coroutines.launch

import java.io.File
import java.io.IOException
import java.util.*

class FormBaru : AppCompatActivity() {
    companion object {
        private const val cameraRequestCode = 1
    }

    private var imagePath: String = ""
    private lateinit var database: DatabaseReference
    private lateinit var storage: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_baru)

        // Atur Firebase.
        database = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_document))
        storage = FirebaseStorage.getInstance().getReference(getString(R.string.firebase_storage))

        // Delegasi listeners.
        btnKembali.setOnClickListener { openKembaliMenu() }
        btnFoto.setOnClickListener { prosesKamera() }
        btnSubmit.setOnClickListener { handleSubmission() }
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
                        placeholderGambar.setImageBitmap(BitmapFactory.decodeFile(it.absolutePath))
                    }
                }
            }
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
        val nama = namaTamu.text.toString()
        val tujuan = tujuanTamu.text.toString()
        val plat = platTamu.text.toString()
        val jamMasuk = (System.currentTimeMillis() / 1000)
        val jamKeluar = 0L

        if (nama.isNotBlank() && tujuan.isNotBlank() && plat.isNotBlank() && placeholderGambar.drawable !== null) {
            val gambarCloud = Uri.fromFile(File(imagePath))
            val ref = storage.child(jamMasuk.toString())

            ref.putFile(gambarCloud).addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }.addOnSuccessListener { res ->
                res.let {
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        val tamu = Tamu(nama, tujuan, plat, jamMasuk, jamKeluar, uri.toString())
                        val tamuId = database.push().key.toString()

                        database.child(getString(R.string.firebase_document)).child(tamuId).setValue(tamu).addOnCompleteListener {
                            namaTamu.setText("")
                            tujuanTamu.setText("")
                            platTamu.setText("")
                            placeholderGambar.setImageResource(0)
                            Snackbar.make(formbaru, getString(R.string.success_upload), Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        } else {
            Snackbar.make(formbaru, getString(R.string.required_fields), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun openKembaliMenu() {
        startActivity(Intent(this@FormBaru, MainMenu::class.java))
    }
}