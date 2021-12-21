package be.scryper.sos;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private static final int ALARME_CODE = 100;

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

        authenticateResult = getIntent().getParcelableExtra(LoginActivity.KEY_LOGIN);

        initUI();

        initOnCLickListeners();

        getMeetings(authenticateResult.getId());


    }

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

        lvDailyMeetings.setOnItemClickListener((adapterView, view, i, l)->{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if(adapter.getItem(i).getSchedule().isBefore(LocalDateTime.now())){
                    Toast.makeText(
                            getApplicationContext(),
                            "Impossible to set an alarm in the past",
                            Toast.LENGTH_LONG).show();

                }else{
                    buildAlarmToast(adapter.getItem(i).getSchedule());
                }
            }

        });
    }

    public void initOnCLickListeners(){
        btnProject.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ProjectActivity.class);
            intent.putExtra(LoginActivity.KEY_LOGIN, authenticateResult);

            startActivity(intent);
        });


        btnMeeting.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MeetingActivity.class);
            intent.putExtra(LoginActivity.KEY_LOGIN, authenticateResult);

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
                            if(test.getDayOfYear() == LocalDateTime.now().getDayOfYear()&& test.getYear() == LocalDateTime.now().getYear()){
                                dtoFinal = DtoMeeting.combine(dto.get(i),test);
                                //ajout du dto à la liste d'auj
                                adapter.add(dtoFinal);
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

    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(HomeActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[] { permission }, requestCode);
            Log.e("dotni","jisipa pq jsuis al");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.
                onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == ALARME_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(HomeActivity.this, "Camera Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(HomeActivity.this, "Camera Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
    }

    public void buildAlarmToast(LocalDateTime time){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText edittext = new EditText(getApplicationContext());
        String titleText = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            titleText = "Do you want to add an alarm at " + (time.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).toString();
        }

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.BLUE);

        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        // Set the alert dialog title using spannable string builder
        builder.setTitle(ssBuilder);
        //Setting message manually and performing action on button click
        builder .setCancelable(true)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Add Alarm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        checkPermission(Manifest.permission.SET_ALARM,ALARME_CODE);
                        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            intent.putExtra(AlarmClock.EXTRA_HOUR,time.getHour());
                            intent.putExtra(AlarmClock.EXTRA_MINUTES,time.getMinute());
                            intent.putExtra(AlarmClock.EXTRA_SKIP_UI,true);
                        }
                        startActivity(intent);
                    }
                });


        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setView(edittext);

        //Setting the title manually
        alert.show();
    }


}