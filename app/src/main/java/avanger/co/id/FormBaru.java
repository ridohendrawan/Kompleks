package avanger.co.id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormBaru extends AppCompatActivity {
    private final int cameraRequestCode = 1;
    private String pathFoto = null;
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
                File fileGambar = null;

                try {
                    fileGambar = membuatGambar();
                } catch (IOException e) {
                    // abaikan untuk sekarang...
                    Log.d("e", e.getMessage());
                }

                if (fileGambar != null) {
                    Uri photoUri = FileProvider.getUriForFile(
                            getApplicationContext(),
                            "avanger.co.id.fileprovider",
                            fileGambar
                    );

                    intentAmbilFoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intentAmbilFoto, cameraRequestCode);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == cameraRequestCode && resultCode == RESULT_OK) {
            File gambar = new File(pathFoto);

            Log.i("i", gambar.getAbsolutePath());

            if (gambar.exists()) {
                placeholderGambar.setImageURI(Uri.fromFile(gambar));
            }
        }
    }

    private File membuatGambar() throws IOException {
        // Membuat filename.
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String namaFileGambar = timestamp + "_";
        File direktoriPenyimpanan = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File gambar = File.createTempFile(namaFileGambar, ".jpg", direktoriPenyimpanan);

        // Simpan gambar tersebut.
        pathFoto = gambar.getAbsolutePath();
        return gambar;
    }

    public void openKembaliMenu (){
        Intent intent = new Intent(FormBaru.this, MainMenu.class);
        startActivity(intent);
    }
}