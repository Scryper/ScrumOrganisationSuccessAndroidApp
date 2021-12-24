package be.scryper.sos;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.dto.DtoInputMeeting;
import be.scryper.sos.dto.DtoMeeting;
import be.scryper.sos.helpers.SessionManager;
import be.scryper.sos.infrastructure.IMeetingRepository;
import be.scryper.sos.infrastructure.Retrofit;
import be.scryper.sos.ui.MeetingArrayAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetingActivity extends AppCompatActivity {

    private Button btnBack;
    private ListView lvMeetings;
    private DtoAuthenticateResult authenticateResult;
    private MeetingArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        //get the intent from the login
        authenticateResult = getIntent().getParcelableExtra(LoginActivity.KEY_LOGIN);

        //init UI and fill listview
        initUI();
        initOnCLickListeners();
        getMeetings(authenticateResult.getId());
    }

    //init the onClickListener of each button
    private void initOnCLickListeners() {
        btnBack.setOnClickListener(view -> {
            this.finish();
        });
    }

    //init the listview empty
    public void initUI(){
        btnBack = findViewById(R.id.btn_meetingActivity_back);
        lvMeetings = findViewById(R.id.lv_meetingActivity_meetings);

        adapter = new MeetingArrayAdapter(
                this,
                new ArrayList<>()
        );

        lvMeetings.setAdapter(adapter);
    }

    //get the whole list of meetings
    private void getMeetings(int idUser) {
        SessionManager sessionManager = new SessionManager(this);
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
                        Date date = null;
                        try {
                            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(dto.get(i).getSchedule());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(date.after(new Date())){
                            dtoFinal = DtoMeeting.combine(dto.get(i), date);
                            //ajout du dto à la liste d'auj
                            adapter.add(dtoFinal);
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
                Log.e("Error", t.toString());
            }
        });
    }
}