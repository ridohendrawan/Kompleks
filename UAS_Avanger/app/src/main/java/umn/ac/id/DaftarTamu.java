package umn.ac.id;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DaftarTamu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_tamu);
        Button btnKembaliMenu,btnData1;
        btnKembaliMenu = findViewById(R.id.KembaliMenu);
        btnData1 = findViewById(R.id.data1);

        btnKembaliMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openKembaliMenu();
            }
        });

        btnData1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openData1();
            }
        });
    }

    public void openKembaliMenu (){
        Intent intent = new Intent(DaftarTamu.this, MainMenu.class);
        startActivity(intent);
    }
    public void openData1 (){
        Intent intent = new Intent(DaftarTamu.this, DetailDaftarTamu.class);
        startActivity(intent);
    }
}
