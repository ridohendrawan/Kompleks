package avanger.co.id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int cameraRequestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pengaturan Shared Preferences.
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_isLoggedIn),
                Context.MODE_PRIVATE
        );

        // Jika sudah memiliki shared preferences, langsung masuk.
        if (sharedPref.getBoolean(getString(R.string.preference_isLoggedIn), false)) {
            openMainMenu();
        }

        Button btnLupaPassword, btnLogin;
        EditText loginText, passwordText;

        btnLupaPassword = findViewById(R.id.lupapassword);
        loginText = findViewById(R.id.username);
        passwordText = findViewById(R.id.pass);
        btnLogin = findViewById(R.id.Login);

        btnLupaPassword.setOnClickListener((v) -> openLupaPassword());

        btnLogin.setOnClickListener((v) -> {
            String username = loginText.getText().toString();
            String password = passwordText.getText().toString();

            // Ketika seorang user berhasil login, setting 'sharedPreference' menjadi true.
            // Hal ini mencegah login ulang ketika seseorang mengakses aplikasi lagi.
            // Segi security, aman karena hanya menyimpan boolean variable.
            if (username.equals("user") && (password.equals("123"))) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.preference_isLoggedIn), true);
                editor.apply();

                openMainMenu();
            } else {
                Toast.makeText(MainActivity.this, "Username / Password Salah.", Toast.LENGTH_SHORT).show();
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
    }

    public void openLupaPassword() {
        Intent intent = new Intent(MainActivity.this, LupaPassword.class);
        startActivity(intent);
    }

    public void openMainMenu() {
        Intent intent = new Intent(MainActivity.this, MainMenu.class);
        startActivity(intent);
    }
}