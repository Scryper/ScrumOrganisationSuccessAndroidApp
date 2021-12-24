package be.scryper.sos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import be.scryper.sos.dto.DtoAuthenticateRequest;
import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.helpers.SessionManager;
import be.scryper.sos.infrastructure.Retrofit;
import be.scryper.sos.infrastructure.IUserRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static final String KEY_LOGIN = "authenticateResult";
    private String[] mails = {"florian.mazzeo@gmail.com","damsover@gmail.com","martin.maes100.000@gmail.com"};
    private AutoCompleteTextView mail;
    private TextView password;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mail = findViewById(R.id.et_mainActivity_mail);
        password = findViewById(R.id.et_mainActivity_password);
        Button btnConnect = findViewById(R.id.btn_mainActivity_submit);

        sessionManager = new SessionManager(this);

        //create the arrayAdapter for the emails
        ArrayAdapter<String> adapterMails = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mails);

        //autofill for the emails
        mail.setAdapter(adapterMails);

        // Set the minimum number of characters, to show suggestions
        mail.setThreshold(0);

        btnConnect.setOnClickListener(view -> {
            //function to try to log in the user
            authenticate(new DtoAuthenticateRequest(mail.getText().toString(), password.getText().toString()));
        });
    }

    //try to authenticate the user
    private void authenticate(DtoAuthenticateRequest authentication) {

        Retrofit.getInstance().create(IUserRepository.class)
                .authenticate(authentication).enqueue(new Callback<DtoAuthenticateResult>() {
                    @Override
                    public void onResponse(Call<DtoAuthenticateResult> call, Response<DtoAuthenticateResult> response) {

                        if(response.code() == 200) {

                            sessionManager.saveAuthToken(response.body().getToken());

                            Log.e("dotni", sessionManager.fetchAuthToken());

                            //if the user informations are OK, then the user goes to HomeActivity
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            DtoAuthenticateResult authenticateResult = response.body();
                            intent.putExtra(KEY_LOGIN, authenticateResult);
                            startActivity(intent);
                        }
                        else{
                             Toast.makeText(
                                     getApplicationContext(),
                                     "Bad credentials",
                                     Toast.LENGTH_SHORT
                             ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DtoAuthenticateResult> call, Throwable t) {
                        Log.e("Error", t.toString());
                    }
                });
    }
}