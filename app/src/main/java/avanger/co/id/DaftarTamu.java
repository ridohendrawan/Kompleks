package avanger.co.id;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

public class DaftarTamu extends AppCompatActivity {
    static {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_tamu);

        // Atur Firebase.
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Button btnKembaliMenu = findViewById(R.id.KembaliMenu);
        Button btnData1 = findViewById(R.id.data1);

        btnKembaliMenu.setOnClickListener((v) -> finish());
        btnData1.setOnClickListener((v) -> openData1());
    }

    public void openData1() {
        Intent intent = new Intent(DaftarTamu.this, DetailDaftarTamu.class);
        startActivity(intent);
    }
}
