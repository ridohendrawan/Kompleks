package avanger.co.id

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.*
import avanger.co.id.databinding.ActivityMainMenuBinding

class MainMenu : AppCompatActivity() {
    companion object {
        private const val cameraRequestCode = 1
    }

    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.KembaliMenu.setOnClickListener { finishAndRemoveTask() }
        binding.formbaru.setOnClickListener { openFormBaru() }
        binding.daftartamu.setOnClickListener { openDaftarTamu() }
        binding.daftarTamuSelesai.setOnClickListener { openDaftarTamuSelesai() }
        binding.panduanpengguna.setOnClickListener { openPanduanPengguna() }

        // Run workers for data deletion.
        val workRequest = OneTimeWorkRequestBuilder<DeleteTask>().build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)

        // Manajemen permissions ada disini.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequestCode)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequestCode)
            }
        }
    }

    private fun openFormBaru() {
        val intent = Intent(this@MainMenu, FormBaru::class.java)
        startActivity(intent)
    }

    private fun openDaftarTamu() {
        val intent = Intent(this@MainMenu, DaftarTamuMasuk::class.java)
        startActivity(intent)
    }

    private fun openPanduanPengguna() {
        val intent = Intent(this@MainMenu, PanduanPengguna::class.java)
        startActivity(intent)
    }

    private fun openDaftarTamuSelesai() {
        val intent = Intent(this@MainMenu, DaftarTamuKeluar::class.java)
        startActivity(intent)
    }
}