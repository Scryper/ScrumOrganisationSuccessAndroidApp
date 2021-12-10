package be.scryper.sos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.scryper.sos.domain.Sprint;
import be.scryper.sos.domain.UserStory;
import be.scryper.sos.ui.UserStoryArrayAdapter;

public class SprintActivity extends AppCompatActivity {
    public static final String KEY_USER_STORY = "userStory";
    private ListView lvSimple;
    private TextView tvId;
    private TextView tvDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint);

        tvId = findViewById(R.id.tv_sprintActivity_ph_id);
        tvDescription = findViewById(R.id.tv_sprintActivity_ph_description);

        Sprint sprint = getIntent().getParcelableExtra(ProjectActivity.KEY_SPRINT);

        tvId.setText(String.valueOf(sprint.getId()));
        tvDescription.setText(sprint.getDescription());
        initUserStoryList();


    }

    private void initUserStoryList() {
        lvSimple = findViewById(R.id.lv_sprintActivity_simpleList);

        List<UserStory> userStories = new ArrayList<>();
        userStories.add(new UserStory(1, "Inception", "desc Inception", "true"));
        userStories.add(new UserStory(2, "Grrrrrrr", "desc Grrrrrrr", "false"));

        UserStoryArrayAdapter adapter = new UserStoryArrayAdapter(
                getApplicationContext(),
                userStories
        );

        lvSimple.setAdapter(adapter);

        lvSimple.setOnItemClickListener((adapterView, view, i, l) -> {
            UserStory userStory = (UserStory) adapterView.getItemAtPosition(i);
            /*Toast.makeText(
                    getApplicationContext(),
                    movie.getName() + " " + movie.getRating(),
                    Toast.LENGTH_LONG
            ).show();*/
            Intent intent = new Intent(SprintActivity.this, UserStoryActivity.class);
            intent.putExtra(KEY_USER_STORY, userStory);

            startActivity(intent);
        });
    }
}