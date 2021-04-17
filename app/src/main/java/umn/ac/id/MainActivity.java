package umn.ac.id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final int cameraRequestCode = 1;
    private final int writeExternalRequestCode = 2;
    private final int readExternalRequestCode = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLupaPassword,btnLogin;
        EditText loginText, passwordText;

        btnLupaPassword = findViewById(R.id.lupapassword);
        loginText = findViewById(R.id.username);
        passwordText = findViewById(R.id.pass);
        btnLogin = findViewById(R.id.Login);

        btnLupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLupaPassword();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginText.getText().toString();
                String password = passwordText.getText().toString();

                if(username.equals("user") && (password.equals("123"))) {
//                    Toast.makeText(tampilan_login.this, "Selamat datang, Toast.LENGTH_SHORT).show();
                    openMainMenu();
                    //TampilDialog();
                }else{
                    Toast.makeText(MainActivity.this, "Username / Password Salah.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Manajemen permissions ada disini.
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.CAMERA},
                        cameraRequestCode
                );
            } else {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        cameraRequestCode
                );
            }
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        writeExternalRequestCode
                );
            } else {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        writeExternalRequestCode
                );
            }
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        readExternalRequestCode
                );
            } else {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        readExternalRequestCode
                );
            }
        }
    }

    public void openLupaPassword (){
        Intent intent = new Intent(MainActivity.this, LupaPassword.class);
        startActivity(intent);
    }

    public void openMainMenu (){
        Intent intent = new Intent(MainActivity.this, MainMenu.class);
        startActivity(intent);
    }
}