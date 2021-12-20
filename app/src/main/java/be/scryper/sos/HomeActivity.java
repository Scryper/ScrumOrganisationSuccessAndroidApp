package be.scryper.sos;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.dto.DtoMeeting;
import be.scryper.sos.dto.DtoProject;
import be.scryper.sos.infrastructure.IMeetingRepository;
import be.scryper.sos.infrastructure.IProjectRepository;
import be.scryper.sos.infrastructure.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private Button btnProject;
    private Button btnMeeting;

    private TextView tvHello;

    private ListView lvDailyMeetings;

    private DtoAuthenticateResult authenticateResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        authenticateResult = getIntent().getParcelableExtra(MainActivity.KEY_LOGIN);

        initUI();

        initOnCLickListeners();

        Log.e("dotni", String.valueOf(authenticateResult.getId()));
        getMeeting(authenticateResult.getId());

    }


    /*public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(HomeActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(HomeActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CALENDAR_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(HomeActivity.this, "Camera Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(HomeActivity.this, "Camera Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
    }*/

    public void initUI(){
        btnProject = findViewById(R.id.btn_homeActivity_Projet);
        btnMeeting = findViewById(R.id.btn_homeActivity_Meeting);
        tvHello = findViewById(R.id.tv_homeActivity_Bonjour);
        lvDailyMeetings = findViewById(R.id.lv_homeActivity_Meetings);
        tvHello.setText("Hello " + authenticateResult.getFirstname());

        //requete pour récup les meetings

        //inflate la list view pour chaque meeting du jour

    }

    public void initOnCLickListeners(){
        btnProject.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ProjectActivity.class);
            intent.putExtra(MainActivity.KEY_LOGIN, authenticateResult);

            startActivity(intent);
        });


        btnMeeting.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MeetingActivity.class);
            intent.putExtra(MainActivity.KEY_LOGIN, authenticateResult);

            startActivity(intent);
        });
    }

    private void getMeeting(int idUser) {
        /*Retrofit.getInstance().create(IMeetingRepository.class)
                .getByIdUser(idUser).enqueue(new Callback<List<DtoMeeting>>() {
            @Override
            public void onResponse(Call<List<DtoMeeting>> call, Response<List<DtoMeeting>> response) {
                if(response.code() == 200){
                    Log.e("dotni","works");
                }
            }

            @Override
            public void onFailure(Call<List<DtoMeeting>> call, Throwable t) {
                Log.e("dotni", t.toString());

            }
        });*/

        Retrofit.getInstance().create(IMeetingRepository.class).getById(1).enqueue(new Callback<DtoMeeting>() {
            @Override
            public void onResponse(Call<DtoMeeting> call, Response<DtoMeeting> response) {

            }

            @Override
            public void onFailure(Call<DtoMeeting> call, Throwable t) {
                Log.e("dotni", call.request().url().toString());
                Log.e("dotni", t.toString());
            }
        });
    }



}