package be.scryper.sos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;

import be.scryper.sos.dto.DtoAuthenticateResult;

public class WelcomeActivity extends AppCompatActivity {
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnLogin = findViewById(R.id.btn_welcomeActivity_login);

        btnLogin.setOnClickListener(view ->{
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}