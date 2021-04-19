package avanger.co.id;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button btnKembaliMenu, btnFormBaru, btnDaftarTamu, btnPanduanPengguna;
        btnKembaliMenu = findViewById(R.id.KembaliMenu);
        btnFormBaru = findViewById(R.id.formbaru);
        btnDaftarTamu = findViewById(R.id.daftartamu);
        btnPanduanPengguna = findViewById(R.id.panduanpengguna);


        btnKembaliMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openKembaliMenu();
            }
        });

        btnFormBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFormBaru();
            }
        });
        btnDaftarTamu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDaftarTamu();
            }
        });
        btnPanduanPengguna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPanduanPengguna();
            }
        });
    }

    public void openKembaliMenu() {
        Intent intent = new Intent(MainMenu.this, MainActivity.class);
        startActivity(intent);
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