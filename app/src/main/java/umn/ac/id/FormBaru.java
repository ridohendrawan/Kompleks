package umn.ac.id;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FormBaru extends AppCompatActivity {
    private final int cameraRequestCode = 1;
    private Button btnKembaliMenu, btnKamera;
    private ImageView placeholderGambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_baru);

        btnKembaliMenu = findViewById(R.id.KembaliMenu);
        btnKamera = findViewById(R.id.btnFoto);
        placeholderGambar = findViewById(R.id.placeholderGambar);

        btnKembaliMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openKembaliMenu();
            }
        });

        btnKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAmbilFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                try {
                    startActivityForResult(intentAmbilFoto, cameraRequestCode);
                } catch (ActivityNotFoundException e) {
                    // abaikan untuk sekarang...
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == cameraRequestCode && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap gambar = (Bitmap) extras.get("data");
            placeholderGambar.setImageBitmap(gambar);
        }
    }

    public void openKembaliMenu (){
        Intent intent = new Intent(FormBaru.this, MainMenu.class);
        startActivity(intent);
    }
}