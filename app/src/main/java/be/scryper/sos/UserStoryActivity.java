package be.scryper.sos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import be.scryper.sos.domain.Comment;
import be.scryper.sos.domain.Sprint;
import be.scryper.sos.domain.UserStory;
import be.scryper.sos.ui.CommentArrayAdapter;
import be.scryper.sos.ui.UserStoryArrayAdapter;

public class UserStoryActivity extends AppCompatActivity {
    private ListView lvSimple;
    private TextView tvName;
    private TextView tvDescription;
    private TextView tvIsDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_story);

        tvName = findViewById(R.id.tv_userStoryActivity_ph_name);
        tvDescription = findViewById(R.id.tv_userStoryActivity_ph_description);
        tvIsDone = findViewById(R.id.tv_userStoryActivity_ph_isDone);

        UserStory userStory = getIntent().getParcelableExtra(SprintActivity.KEY_USER_STORY);

        tvName.setText(userStory.getName());
        tvDescription.setText(userStory.getDescription());
        tvIsDone.setText(userStory.getDone());

        initCommentList();
    }

    private void initCommentList() {
        lvSimple = findViewById(R.id.lv_userStoryActivity_simpleList);

        List<Comment> comments = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            comments.add(new Comment("Inception", LocalDateTime.now()));
            comments.add(new Comment("Grrrrrr", LocalDateTime.now()));
        }

        CommentArrayAdapter adapter = new CommentArrayAdapter(
                getApplicationContext(),
                comments
        );

        lvSimple.setAdapter(adapter);
    }
}