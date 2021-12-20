package be.scryper.sos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.time.LocalDateTime;

import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.dto.DtoInputUser;
import be.scryper.sos.dto.DtoUser;
import be.scryper.sos.infrastructure.IUserRepository;
import be.scryper.sos.infrastructure.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private TextView tvFirstname;
    private TextView tvLastname;
    private TextView etFirstname;
    private TextView etLastname;
    private Button btnFirstname;
    private Button btnLastname;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvFirstname = findViewById(R.id.tv_profileActivity_ph_firstname);
        tvLastname = findViewById(R.id.tv_profileActivity_ph_lastname);
        btnFirstname = findViewById(R.id.btn_profileActivity_firstname);
        btnLastname = findViewById(R.id.btn_profileActivity_lastname);
        etFirstname = findViewById(R.id.et_profileActivity_firstname);
        etLastname = findViewById(R.id.et_profileActivity_lastname);
        DtoAuthenticateResult authenticateResult = getIntent().getParcelableExtra(MainActivity.KEY_LOGIN);

        tvFirstname.setText(authenticateResult.getFirstname());
        tvLastname.setText(authenticateResult.getLastname());

        btnFirstname.setOnClickListener(view -> {
            String firstname = etFirstname.getText().toString();
            updateFirstname(firstname);
        });

        btnLastname.setOnClickListener(view -> {
            String lastname = etLastname.getText().toString();
            updateLastname(lastname);
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateLastname(String lastname) {
        DtoAuthenticateResult authenticateResult = getIntent().getParcelableExtra(MainActivity.KEY_LOGIN);
        DtoInputUser dtoUser = new DtoInputUser(authenticateResult.getFirstname(), lastname, "", "", 0, LocalDateTime.now().toString());
        updateFirstnameLastname(authenticateResult.getId(), dtoUser);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateFirstname(String firstname) {
        DtoAuthenticateResult authenticateResult = getIntent().getParcelableExtra(MainActivity.KEY_LOGIN);
        DtoInputUser dtoUser = new DtoInputUser(firstname, authenticateResult.getLastname(), "", "", 0, LocalDateTime.now().toString());
        updateFirstnameLastname(authenticateResult.getId(), dtoUser);
    }

    private void updateFirstnameLastname(int id, DtoInputUser dtoUser) {
        Retrofit.getInstance().create(IUserRepository.class)
                .updateFirstnameLastname(id, dtoUser).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                //Log.i("findproblem", response.toString());
                if(response.code() == 200) {
                    Log.i("Todo", "success");
                }
                else{
                    Log.i("Todo", "success different");
                    Log.i("Todo", String.valueOf(response.code()));
                    Log.i("Todo", String.valueOf(dtoUser.toString()));
                    try {
                        Log.i("Todo", String.valueOf(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("Todo", "failure");

            }
        });
    }

}