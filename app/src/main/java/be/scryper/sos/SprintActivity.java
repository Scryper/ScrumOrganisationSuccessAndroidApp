package be.scryper.sos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.dto.DtoSprint;
import be.scryper.sos.dto.DtoSprintUserStory;
import be.scryper.sos.dto.DtoUserStory;
import be.scryper.sos.infrastructure.ISprintUserStoryRepository;
import be.scryper.sos.infrastructure.IUserStoryRepository;
import be.scryper.sos.infrastructure.Retrofit;
import be.scryper.sos.ui.UserStoryArrayAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SprintActivity extends AppCompatActivity {
    public static final String KEY_USER_STORY = "userStory";
    private ListView lvSimple;
    private TextView tvId;
    private TextView tvDescription;

    static List<DtoUserStory> listUserStories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint);
        lvSimple = findViewById(R.id.lv_sprintActivity_simpleList);
        tvId = findViewById(R.id.tv_sprintActivity_ph_id);
        tvDescription = findViewById(R.id.tv_sprintActivity_ph_description);

        DtoSprint sprint = getIntent().getParcelableExtra(ProjectActivity.KEY_SPRINT);

        tvId.setText(String.valueOf(sprint.getId()));
        tvDescription.setText(sprint.getDescription());

        getSprintUserStory(sprint.getId());



    }

    private void getSprintUserStory(int id) {
        Retrofit.getInstance().create(ISprintUserStoryRepository.class)
                .getByIdSprint(id).enqueue(new Callback<List<DtoSprintUserStory>>() {
            @Override
            public void onResponse(Call<List<DtoSprintUserStory>> call, Response<List<DtoSprintUserStory>> response) {
                if (response.code() == 200) {
                    List<DtoSprintUserStory> sprintUserStories = response.body();
                    listUserStories.clear();
                    getUserStories(sprintUserStories);


                } else {
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
            public void onFailure(Call<List<DtoSprintUserStory>> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        t.toString(),
                        Toast.LENGTH_LONG
                ).show();

            }
        });
    }

    private void getUserStories(List<DtoSprintUserStory> sprintUserStories) {
        for (DtoSprintUserStory sprintUserStory:
             sprintUserStories) {

            Retrofit.getInstance().create(IUserStoryRepository.class)
                    .getById(sprintUserStory.getIdUserStory()).enqueue(new Callback<DtoUserStory>() {
                @Override
                public void onResponse(Call<DtoUserStory> call, Response<DtoUserStory> response) {
                    if (response.code() == 200) {
                        DtoUserStory usTmp = response.body();

                        listUserStories.add(usTmp);
                        initUserStoryList();


                    } else {
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
                public void onFailure(Call<DtoUserStory> call, Throwable t) {
                    Toast.makeText(
                            getApplicationContext(),
                            t.toString(),
                            Toast.LENGTH_LONG
                    ).show();

                }
            });
        }

    }

    private void initUserStoryList() {

        UserStoryArrayAdapter adapter = new UserStoryArrayAdapter(
                getApplicationContext(),
                listUserStories
        );

        lvSimple.setAdapter(adapter);

        lvSimple.setOnItemClickListener((adapterView, view, i, l) -> {
            DtoUserStory userStory = (DtoUserStory) adapterView.getItemAtPosition(i);
            DtoAuthenticateResult authenticateResult = getIntent().getParcelableExtra(MainActivity.KEY_LOGIN);

            Intent intent = new Intent(SprintActivity.this, UserStoryActivity.class);
            intent.putExtra(KEY_USER_STORY, userStory);
            intent.putExtra(MainActivity.KEY_LOGIN, authenticateResult);

            startActivity(intent);
        });
    }
}