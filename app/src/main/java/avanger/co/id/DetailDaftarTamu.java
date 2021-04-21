package avanger.co.id;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class DetailDaftarTamu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_daftar_tamu);

        Button btnKembaliMenu = findViewById(R.id.KembaliMenu);

        btnKembaliMenu.setOnClickListener((v) -> finish());
    }
}