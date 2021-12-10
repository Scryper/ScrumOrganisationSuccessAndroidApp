package be.scryper.sos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import be.scryper.sos.domain.Sprint;
import be.scryper.sos.ui.SprintArrayAdapter;

public class ProjectActivity extends AppCompatActivity {
    public static final String KEY_SPRINT = "sprint";
    private ListView lvSimple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        initSprintList();
    }

    private void initSprintList() {
        lvSimple = findViewById(R.id.lv_projectActivity_simpleList);

        List<Sprint> sprints = new ArrayList<>();
        sprints.add(new Sprint(1, "Inception"));
        sprints.add(new Sprint(2, "Grrrrrrr"));

        SprintArrayAdapter adapter = new SprintArrayAdapter(
                getApplicationContext(),
                sprints
        );

        lvSimple.setAdapter(adapter);

        lvSimple.setOnItemClickListener((adapterView, view, i, l) -> {
            Sprint sprint = (Sprint) adapterView.getItemAtPosition(i);
            /*Toast.makeText(
                    getApplicationContext(),
                    movie.getName() + " " + movie.getRating(),
                    Toast.LENGTH_LONG
            ).show();*/
            Intent intent = new Intent(ProjectActivity.this, SprintActivity.class);
            intent.putExtra(KEY_SPRINT, sprint);

            startActivity(intent);
        });
    }
}