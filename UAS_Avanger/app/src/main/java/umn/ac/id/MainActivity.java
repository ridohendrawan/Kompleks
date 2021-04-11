package umn.ac.id;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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