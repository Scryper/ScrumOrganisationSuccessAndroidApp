package be.scryper.sos;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.dto.DtoInputMeeting;
import be.scryper.sos.dto.DtoMeeting;
import be.scryper.sos.helpers.SessionManager;
import be.scryper.sos.infrastructure.IMeetingRepository;
import be.scryper.sos.infrastructure.Retrofit;
import be.scryper.sos.ui.TodayMeetingArrayAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private static final int ALARM_CODE = 100;

    private Button btnProject;
    private Button btnMeeting;
    private DtoAuthenticateResult authenticateResult;
    private TodayMeetingArrayAdapter adapter;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sessionManager = new SessionManager(this);

        //get intent from login activity
        authenticateResult = getIntent().getParcelableExtra(LoginActivity.KEY_LOGIN);

        initUI();
        initOnCLickListeners();
        getMeetings(authenticateResult.getId());
    }

    //init UI with empty list view
    public void initUI(){
        btnProject = findViewById(R.id.btn_homeActivity_project);
        btnMeeting = findViewById(R.id.btn_homeActivity_meeting);
        TextView tvHello = findViewById(R.id.tv_homeActivity_bonjour);
        ListView lvDailyMeetings = findViewById(R.id.lv_homeActivity_meetings);
        tvHello.setText("Hello " + authenticateResult.getFirstname());

        adapter = new TodayMeetingArrayAdapter(
                this,
                new ArrayList<>()
        );

        lvDailyMeetings.setAdapter(adapter);

        lvDailyMeetings.setOnItemClickListener((adapterView, view, i, l)->{
                if(adapter.getItem(i).getSchedule().before(new Date())){
                    Toast.makeText(
                            getApplicationContext(),
                            "Impossible to set an alarm in the past",
                            Toast.LENGTH_LONG
                    ).show();
                }else{
                    buildAlarmToast(adapter.getItem(i).getSchedule());
                }
        });
    }

    //init the buttons of the activity
    public void initOnCLickListeners(){
        //Button to go to the project
        btnProject.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ProjectActivity.class);
            intent.putExtra(LoginActivity.KEY_LOGIN, authenticateResult);
            startActivity(intent);
        });

        //Button to show the whole list of meetings
        btnMeeting.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MeetingActivity.class);
            intent.putExtra(LoginActivity.KEY_LOGIN, authenticateResult);
            startActivity(intent);
        });
    }

    //Get the list of daily meetings
    private void getMeetings(int idUser) {

        Retrofit.getInstance().create(IMeetingRepository.class)
                .getByIdUser(idUser,"Bearer "+sessionManager.fetchAuthToken()).enqueue(new Callback<List<DtoInputMeeting>>() {
            @Override
            public void onResponse(Call<List<DtoInputMeeting>> call, Response<List<DtoInputMeeting>> response) {
                if(response.code() == 200){
                    //récupération de la réponse
                    List<DtoInputMeeting> dto = response.body();
                    //on parcourt pour récuperer le string et le transformer en date
                    for(int i =0; i<dto.size();i++){
                        DtoMeeting dtoFinal;
                        Date today;
                        try {
                            //check if the meeting is today before adding to the listview
                            today = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH).parse(dto.get(i).getSchedule());
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(new Date());
                            cal.add(Calendar.DATE, 1);
                            Date tomorrow = cal.getTime();
                            if(today.after(new Date()) && today.before(tomorrow)){
                                dtoFinal = DtoMeeting.combine(dto.get(i),today);
                                adapter.add(dtoFinal);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    Toast.makeText(
                            getApplicationContext(),
                            "Something went wrong",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<List<DtoInputMeeting>> call, Throwable t) {
                Log.e("Error", call.toString());
            }
        });
    }

    // Checking if permission is not granted
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[] { permission }, requestCode);
        }
    }

    //build alert to create the alarm
    @SuppressLint("SimpleDateFormat")
    public void buildAlarmToast(Date time){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText edittext = new EditText(getApplicationContext());
        String titleText;
        titleText = "Do you want to add an alarm at " + (new SimpleDateFormat("dd/MM/yyyy HH:mm").format(time));

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
                        //check the permission for the alarm
                        checkPermission(Manifest.permission.SET_ALARM, ALARM_CODE);

                        //create the alarm
                        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                        intent.putExtra(AlarmClock.EXTRA_HOUR,time.getHours());
                        intent.putExtra(AlarmClock.EXTRA_MINUTES,time.getMinutes());
                        intent.putExtra(AlarmClock.EXTRA_SKIP_UI,true);
                        startActivity(intent);
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setView(edittext);
        alert.show();
    }
}