package be.scryper.sos;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.dto.DtoInputMeeting;
import be.scryper.sos.dto.DtoMeeting;
import be.scryper.sos.infrastructure.IMeetingRepository;
import be.scryper.sos.infrastructure.Retrofit;
import be.scryper.sos.ui.TodayMeetingArrayAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private Button btnProject;
    private Button btnMeeting;

    private TextView tvHello;

    private ListView lvDailyMeetings;

    private DtoAuthenticateResult authenticateResult;

    private TodayMeetingArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        authenticateResult = getIntent().getParcelableExtra(MainActivity.KEY_LOGIN);

        initUI();

        initOnCLickListeners();

        Log.e("dotni", String.valueOf(authenticateResult.getId()));
        getMeetings(authenticateResult.getId());

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

        adapter = new TodayMeetingArrayAdapter(
                this,
                new ArrayList<DtoMeeting>()
        );

        lvDailyMeetings.setAdapter(adapter);
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

    private void getMeetings(int idUser) {
        Retrofit.getInstance().create(IMeetingRepository.class)
                .getByIdUser(idUser).enqueue(new Callback<List<DtoInputMeeting>>() {
            @Override
            public void onResponse(Call<List<DtoInputMeeting>> call, Response<List<DtoInputMeeting>> response) {
                if(response.code() == 200){
                    //récupération de la réponse
                    List<DtoInputMeeting> dto = response.body();
                    List<DtoMeeting> dtoMeeting = new ArrayList<DtoMeeting>();
                    //on parcourt pour récuperer le string et le transformer en date
                    for(int i =0; i<dto.size();i++){
                        DtoMeeting dtoFinal;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            LocalDateTime test = LocalDateTime.parse(dto.get(i).getSchedule());
                            Log.e("dotni", test.toString());
                            if(test.getDayOfYear() == LocalDateTime.now().getDayOfYear()&& test.getYear() == LocalDateTime.now().getYear()){
                                dtoFinal = DtoMeeting.combine(dto.get(i),test);

                                //ajout du dto à la liste d'auj
                                adapter.add(dtoFinal);
                                Log.e("dotni", dtoFinal.toString());
                            }
                        }
                    }
                }
                else{
                    Log.e("dotni", call.toString());
                }
            }

            @Override
            public void onFailure(Call<List<DtoInputMeeting>> call, Throwable t) {
                Log.e("dotni", t.toString());
            }
        });


    }



}