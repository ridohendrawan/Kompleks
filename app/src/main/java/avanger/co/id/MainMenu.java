package avanger.co.id;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

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