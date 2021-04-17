package umn.ac.id;

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
        Button btnKembaliMenu;
        btnKembaliMenu = findViewById(R.id.KembaliMenu);

        btnKembaliMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openKembaliMenu();
            }
        });
    }

    public void openKembaliMenu (){
        Intent intent = new Intent(PanduanPengguna.this, MainMenu.class);
        startActivity(intent);
    }
}
