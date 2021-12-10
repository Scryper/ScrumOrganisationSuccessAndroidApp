package be.scryper.sos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private Button btnProject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnProject = findViewById(R.id.btn_homeActivity_project);

        btnProject.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ProjectActivity.class);
            startActivity(intent);
        });
    }
}