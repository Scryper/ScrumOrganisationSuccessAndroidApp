package be.scryper.sos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.dto.DtoComment;
import be.scryper.sos.dto.DtoCreateComment;
import be.scryper.sos.dto.DtoSprint;
import be.scryper.sos.dto.DtoUserStory;
import be.scryper.sos.infrastructure.ICommentRepository;
import be.scryper.sos.infrastructure.ISprintRepository;
import be.scryper.sos.infrastructure.Retrofit;
import be.scryper.sos.ui.CommentArrayAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserStoryActivity extends AppCompatActivity {
    private ListView lvSimple;
    private TextView tvName;
    private TextView tvDescription;
    private TextView tvNewComment;
    private Button btnAddComment;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_story);

        tvName = findViewById(R.id.tv_userStoryActivity_ph_name);
        tvDescription = findViewById(R.id.tv_userStoryActivity_ph_description);
        btnAddComment = findViewById(R.id.btn_userStoryActivity_addComment);
        tvNewComment = findViewById(R.id.et_userStoryActivity_comment);

        DtoUserStory userStory = getIntent().getParcelableExtra(SprintActivity.KEY_USER_STORY);

        DtoAuthenticateResult authenticateResult = getIntent().getParcelableExtra(MainActivity.KEY_LOGIN);

        tvName.setText(userStory.getName());
        tvDescription.setText(userStory.getDescription());

        getComments(userStory.getId());

        btnAddComment.setOnClickListener(view -> {
            int idUserStory = userStory.getId();
            int idUser = authenticateResult.getId();

            LocalDateTime postedAt = LocalDateTime.now();
            postedAt = postedAt.truncatedTo(ChronoUnit.SECONDS);
            String tmp = postedAt.toString();
            String content = tvNewComment.getText().toString();
            DtoCreateComment newComment = new DtoCreateComment(idUserStory, idUser, tmp, content);
            Retrofit.getInstance().create(ICommentRepository.class)
                    .create(newComment).enqueue(new Callback<DtoComment>() {
                @Override
                public void onResponse(Call<DtoComment> call, Response<DtoComment> response) {

                    if (response.code() == 201) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Comment added",
                                Toast.LENGTH_LONG
                        ).show();
                        getComments(idUserStory);
                    } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "error",
                                    Toast.LENGTH_LONG
                            ).show();

                    }
                }

                @Override
                public void onFailure(Call<DtoComment> call, Throwable t) {
                    Toast.makeText(
                            getApplicationContext(),
                            t.toString(),
                            Toast.LENGTH_LONG
                    ).show();

                }
            });
        });
    }

    private void getComments(int id) {
        Retrofit.getInstance().create(ICommentRepository.class)
                .getByIdUserStory(id).enqueue(new Callback<List<DtoComment>>() {
            @Override
            public void onResponse(Call<List<DtoComment>> call, Response<List<DtoComment>> response) {
                if (response.code() == 200) {
                    List<DtoComment> comments = response.body();

                    initCommentList(comments);
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
            public void onFailure(Call<List<DtoComment>> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        t.toString(),
                        Toast.LENGTH_LONG
                ).show();

            }
        });
    }

    private void initCommentList(List<DtoComment> comments) {
        lvSimple = findViewById(R.id.lv_userStoryActivity_simpleList);

        CommentArrayAdapter adapter = new CommentArrayAdapter(
                getApplicationContext(),
                comments
        );

        lvSimple.setAdapter(adapter);
    }
}