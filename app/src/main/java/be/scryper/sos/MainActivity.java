package be.scryper.sos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mail;
    private TextView password;
    private Button btnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mail = findViewById(R.id.et_mainActivity_mail);
        password = findViewById(R.id.et_mainActivity_password);
        btnConnect = findViewById(R.id.btn_mainActivity_submit);

        btnConnect.setOnClickListener(view -> {
            String mailString = mail.getText().toString();
            String passwordString = password.getText().toString();
            String value = mailString + " : " + passwordString;
            if(passwordString.equals("a") && mailString.equals("a")){
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(
                        getApplicationContext(),
                        value,
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }


}