package avanger.co.id

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_baru)

        btnKembali.setOnClickListener() { openKembaliMenu() }
        btnFoto.setOnClickListener() { prosesKamera() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == cameraRequestCode && resultCode == RESULT_OK) {
            val gambar = File(imagePath)

            gambar.let {
                lifecycleScope.launch {
                    val compressedImage = Compressor.compress(applicationContext, it) {
                        default(quality = 10, format = Bitmap.CompressFormat.JPEG)
                        destination(gambar)
                    }

                    compressedImage.let {
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
            val photoUri = FileProvider.getUriForFile(
                    applicationContext,
                    getString(R.string.authority),
                    it
            )

            intentAmbilFoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(intentAmbilFoto, cameraRequestCode)
        }
    }

    /**
     * Fungsi bertugas untuk membuat file kosong dengan filename berupa UNIX timestamp.
     * Akan mengembalikan file tersebut, atau null jika error.
     */
    @Throws(IOException::class)
    private fun membuatFile(): File? {
        try {
            val timestamp = (System.currentTimeMillis() / 1000).toString()
            val direktoriPenyimpanan = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val gambar = File.createTempFile(timestamp, ".jpg", direktoriPenyimpanan)

            imagePath = gambar.absolutePath
            return gambar
        } catch (e: IOException) {
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
        }

        return null
    }

    private fun openKembaliMenu() {
        startActivity(Intent(this@FormBaru, MainMenu::class.java))
    }
}