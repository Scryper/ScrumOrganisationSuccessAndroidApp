package be.scryper.sos;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.dto.DtoInputMeeting;
import be.scryper.sos.dto.DtoMeeting;
import be.scryper.sos.infrastructure.IMeetingRepository;
import be.scryper.sos.infrastructure.Retrofit;
import be.scryper.sos.ui.MeetingArrayAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetingActivity extends AppCompatActivity {

    private Button btnBack;

    private TextView tvDate;
    private TextView tvDescription;

    private ListView lvMeetings;

    private DtoAuthenticateResult authenticateResult;

    private MeetingArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        authenticateResult = getIntent().getParcelableExtra(LoginActivity.KEY_LOGIN);

        initUI();

        initOnCLickListeners();

        getMeetings(authenticateResult.getId());
    }

    private void initOnCLickListeners() {
        btnBack.setOnClickListener(view -> {
            this.finish();
        });
    }

    public void initUI(){
        btnBack = findViewById(R.id.btn_meetingActivity_back);
        tvDate = findViewById(R.id.tv_listViewMeeting_ph_date);
        tvDescription = findViewById(R.id.tv_listViewMeeting_ph_description);
        lvMeetings = findViewById(R.id.lv_meetingActivity_meetings);

        adapter = new MeetingArrayAdapter(
                this,
                new ArrayList<DtoMeeting>()
        );

        lvMeetings.setAdapter(adapter);
    }

    private void getMeetings(int idUser) {
        Retrofit.getInstance().create(IMeetingRepository.class)
                .getByIdUser(idUser).enqueue(new Callback<List<DtoInputMeeting>>() {
            @Override
            public void onResponse(Call<List<DtoInputMeeting>> call, Response<List<DtoInputMeeting>> response) {
                if(response.code() == 200){
                    //récupération de la réponse
                    List<DtoInputMeeting> dto = response.body();

                    //on parcourt pour récuperer le string et le transformer en date
                    for(int i =0; i<dto.size();i++){
                        DtoMeeting dtoFinal;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            try {
                                Date test = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(dto.get(i).getSchedule());
                                if(test.after(new Date())){
                                    dtoFinal = DtoMeeting.combine(dto.get(i),test);
                                    //ajout du dto à la liste d'auj
                                    adapter.add(dtoFinal);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
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