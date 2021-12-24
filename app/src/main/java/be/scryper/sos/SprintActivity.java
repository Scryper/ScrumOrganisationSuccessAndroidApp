package be.scryper.sos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.dto.DtoSprint;
import be.scryper.sos.dto.DtoSprintUserStory;
import be.scryper.sos.dto.DtoUserStory;
import be.scryper.sos.helpers.SessionManager;
import be.scryper.sos.infrastructure.ISprintUserStoryRepository;
import be.scryper.sos.infrastructure.IUserStoryRepository;
import be.scryper.sos.infrastructure.Retrofit;
import be.scryper.sos.ui.UserStoryArrayAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SprintActivity extends AppCompatActivity {
    public static final String KEY_USER_STORY = "userStory";

    private String projectName;
    private ListView lvSimple;
    private TextView tvDescription;
    private TextView tvProjectName;

    static List<DtoUserStory> listUserStories = new ArrayList<>();
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint);
        sessionManager = new SessionManager(this);
        lvSimple = findViewById(R.id.lv_sprintActivity_simpleList);
        tvDescription = findViewById(R.id.tv_sprintActivity_ph_description);
        tvProjectName = findViewById(R.id.tv_sprintActivity_ph_ProjectTitle);

        //get the intents from ProjectActivity
        projectName = getIntent().getExtras().getString(ProjectActivity.KEY_PROJECT);
        DtoSprint sprint = getIntent().getParcelableExtra(ProjectActivity.KEY_SPRINT);

        tvDescription.setText(sprint.getDescription());
        tvProjectName.setText(projectName);

        getSprintUserStory(sprint.getId());
    }

    //get the list of user stories linked to the sprint
    private void getSprintUserStory(int id) {
        Retrofit.getInstance().create(ISprintUserStoryRepository.class)
                .getByIdSprint(id, "Bearer "+sessionManager.fetchAuthToken()).enqueue(new Callback<List<DtoSprintUserStory>>() {
            @Override
            public void onResponse(Call<List<DtoSprintUserStory>> call, Response<List<DtoSprintUserStory>> response) {
                if (response.code() == 200) {
                    List<DtoSprintUserStory> sprintUserStories = response.body();
                    listUserStories.clear();
                    getUserStories(sprintUserStories);


                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Something went wrong",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<List<DtoSprintUserStory>> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }

    //get the list of user stories from the list obtained in getSprintUserStory()
    private void getUserStories(List<DtoSprintUserStory> sprintUserStories) {
        for (DtoSprintUserStory sprintUserStory:
             sprintUserStories) {

            Retrofit.getInstance().create(IUserStoryRepository.class)
                    .getById(sprintUserStory.getIdUserStory(), "Bearer "+sessionManager.fetchAuthToken()).enqueue(new Callback<DtoUserStory>() {
                @Override
                public void onResponse(Call<DtoUserStory> call, Response<DtoUserStory> response) {
                    if (response.code() == 200) {
                        DtoUserStory usTmp = response.body();

                        listUserStories.add(usTmp);
                        initUserStoryList();

                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<DtoUserStory> call, Throwable t) {
                    Log.e("Error", t.toString());
                }
            });
        }

    }

    //init the listview with the user stories
    private void initUserStoryList() {

        UserStoryArrayAdapter adapter = new UserStoryArrayAdapter(
                getApplicationContext(),
                listUserStories
        );

        lvSimple.setAdapter(adapter);

        //on click goes to UserStoryActivity
        lvSimple.setOnItemClickListener((adapterView, view, i, l) -> {
            DtoUserStory userStory = (DtoUserStory) adapterView.getItemAtPosition(i);
            DtoAuthenticateResult authenticateResult = getIntent().getParcelableExtra(LoginActivity.KEY_LOGIN);

            Intent intent = new Intent(SprintActivity.this, UserStoryActivity.class);
            intent.putExtra(KEY_USER_STORY, userStory);
            intent.putExtra(LoginActivity.KEY_LOGIN, authenticateResult);

            startActivity(intent);
        });
    }
}

