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
        Button btnPanduanPengguna = findViewById(R.id.panduanpengguna);

        btnKembaliMenu.setOnClickListener((v) -> finishAndRemoveTask());
        btnFormBaru.setOnClickListener((v) -> openFormBaru());
        btnDaftarTamu.setOnClickListener((v) -> openDaftarTamu());
        btnPanduanPengguna.setOnClickListener((v) -> openPanduanPengguna());

        // Manajemen permissions ada disini.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, cameraRequestCode);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, cameraRequestCode);
            }
        }
    }

    public void openFormBaru() {
        Intent intent = new Intent(MainMenu.this, FormBaru.class);
        startActivity(intent);
    }

    public void openDaftarTamu() {
        Intent intent = new Intent(MainMenu.this, DaftarTamu.class);
        startActivity(intent);
    }

    public void openPanduanPengguna() {
        Intent intent = new Intent(MainMenu.this, PanduanPengguna.class);
        startActivity(intent);
    }
}