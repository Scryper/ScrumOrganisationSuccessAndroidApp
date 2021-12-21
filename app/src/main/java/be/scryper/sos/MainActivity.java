package be.scryper.sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.AlarmClock;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import be.scryper.sos.dto.DtoAuthenticateRequest;
import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.infrastructure.Retrofit;
import be.scryper.sos.infrastructure.IUserRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_LOGIN = "authenticateResult";

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
        mail.setText("la199788@student.helha.be");
        password.setText("myneck");


        btnConnect.setOnClickListener(view -> {
            authenticate(new DtoAuthenticateRequest(mail.getText().toString(), password.getText().toString()));
        });
    }

    private void authenticate(DtoAuthenticateRequest authentication) {

        Retrofit.getInstance().create(IUserRepository.class)
                .authenticate(authentication).enqueue(new Callback<DtoAuthenticateResult>() {
                    @Override
                    public void onResponse(Call<DtoAuthenticateResult> call, Response<DtoAuthenticateResult> response) {
                        //Log.i("Todo", response.toString());

                        if(response.code() == 200) {
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            DtoAuthenticateResult authenticateResult = response.body();
                            intent.putExtra(KEY_LOGIN, (Parcelable) authenticateResult);
                            startActivity(intent);
                        }
                        else{

                            try {
                                Toast.makeText(
                                        getApplicationContext(),
                                        response.errorBody().string(),
                                        Toast.LENGTH_LONG
                                ).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DtoAuthenticateResult> call, Throwable t) {
                        Toast.makeText(
                                getApplicationContext(),
                                call.request().toString(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}