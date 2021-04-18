package avanger.co.id

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import id.zelory.compressor.constraint.destination
import kotlinx.coroutines.launch

import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FormBaru : AppCompatActivity() {
    private val cameraRequestCode = 1
    private var pathFoto: String? = null
    private var placeholderGambar: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_baru)

        val btnKembaliMenu: Button = findViewById(R.id.KembaliMenu)
        val btnKamera: Button = findViewById(R.id.btnFoto)
        placeholderGambar = findViewById(R.id.placeholderGambar)

        btnKembaliMenu.setOnClickListener() { openKembaliMenu() }

        btnKamera.setOnClickListener() {
            val intentAmbilFoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var fileKosong: File? = null

            try {
                fileKosong = membuatFile()
            } catch (e: IOException) {
                // abaikan untuk sekarang...
                Log.d("e", e.message!!)
            }

            if (fileKosong != null) {
                val photoUri = FileProvider.getUriForFile(
                        applicationContext,
                        "avanger.co.id.fileprovider",
                        fileKosong
                )

                intentAmbilFoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(intentAmbilFoto, cameraRequestCode)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == cameraRequestCode && resultCode == RESULT_OK) {
            val gambar = File(pathFoto)

            gambar.let {
                lifecycleScope.launch {
                    val compressedImage = Compressor.compress(applicationContext, it) {
                        default(quality = 30, format = Bitmap.CompressFormat.JPEG)
                        destination(gambar)
                    }

                    compressedImage.let {
                        placeholderGambar?.setImageBitmap(BitmapFactory.decodeFile(it.absolutePath))
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun membuatFile(): File {
        // Membuat filename.
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val namaFileGambar = timestamp + "_"
        val direktoriPenyimpanan = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val gambar = File.createTempFile(namaFileGambar, ".jpg", direktoriPenyimpanan)

        // Mencari path file kosong tersebut berada.
        pathFoto = gambar.absolutePath

        return gambar
    }

    fun openKembaliMenu() {
        val intent = Intent(this@FormBaru, MainMenu::class.java)
        startActivity(intent)
    }
}