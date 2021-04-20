package avanger.co.id;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PanduanPengguna extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panduan_pengguna);

        Button btnKembaliMenu = findViewById(R.id.KembaliMenu);

        btnKembaliMenu.setOnClickListener((v) -> finish());
    }
}
