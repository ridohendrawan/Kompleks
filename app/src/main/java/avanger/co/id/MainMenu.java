package avanger.co.id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {
    private static final int cameraRequestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button btnKembaliMenu = findViewById(R.id.KembaliMenu);
        Button btnFormBaru = findViewById(R.id.formbaru);
        Button btnDaftarTamu = findViewById(R.id.daftartamu);
        Button btnTamuKeluar = findViewById(R.id.daftarTamuSelesai);
        Button btnPanduanPengguna = findViewById(R.id.panduanpengguna);

        btnKembaliMenu.setOnClickListener((v) -> finishAndRemoveTask());
        btnFormBaru.setOnClickListener((v) -> openFormBaru());
        btnDaftarTamu.setOnClickListener((v) -> openDaftarTamu());
        btnPanduanPengguna.setOnClickListener((v) -> openPanduanPengguna());
        btnTamuKeluar.setOnClickListener((v) -> openDaftarTamuSelesai());

        // Manajemen permissions ada disini.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, cameraRequestCode);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, cameraRequestCode);
            }
        }
    }

    private void openFormBaru() {
        Intent intent = new Intent(MainMenu.this, FormBaru.class);
        startActivity(intent);
    }

    private void openDaftarTamu() {
        Intent intent = new Intent(MainMenu.this, DaftarTamuMasuk.class);
        startActivity(intent);
    }

    private void openPanduanPengguna() {
        Intent intent = new Intent(MainMenu.this, PanduanPengguna.class);
        startActivity(intent);
    }

    private void openDaftarTamuSelesai() {
        Intent intent = new Intent(MainMenu.this, DaftarTamuKeluar.class);
        startActivity(intent);
    }
}