package avanger.co.id;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DetailDaftarTamu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_daftar_tamu);
        Button btnKembaliMenu;
        btnKembaliMenu = findViewById(R.id.KembaliMenu);

        btnKembaliMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openKembaliMenu();
            }
        });
    }

    public void openKembaliMenu() {
        Intent intent = new Intent(DetailDaftarTamu.this, DaftarTamu.class);
        startActivity(intent);
    }
}