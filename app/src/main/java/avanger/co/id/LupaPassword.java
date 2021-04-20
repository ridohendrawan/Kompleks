package avanger.co.id;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class LupaPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        Button btnKembaliMain = findViewById(R.id.KembaliMain);

        btnKembaliMain.setOnClickListener((v) -> finish());
    }
}