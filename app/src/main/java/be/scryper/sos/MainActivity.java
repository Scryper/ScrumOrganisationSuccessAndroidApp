package be.scryper.sos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import be.scryper.sos.dto.DtoUser;
import be.scryper.sos.infrastructure.Retrofit;
import be.scryper.sos.infrastructure.repositories.IUserRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        test();

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

    private void test() {
        Retrofit.getInstance().create(IUserRepository.class)
                .getAll().enqueue(new Callback<List<DtoUser>>() {
                    @Override
                    public void onResponse(Call<List<DtoUser>> call, Response<List<DtoUser>> response) {
                        if(response.code() == 200) {
                            Log.i("test", String.valueOf(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DtoUser>> call, Throwable t) {
                        Log.e("error", t.toString());
                    }
                });
    }
}