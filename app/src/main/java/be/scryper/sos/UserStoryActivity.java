package be.scryper.sos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.dto.DtoComment;
import be.scryper.sos.dto.DtoCreateComment;
import be.scryper.sos.dto.DtoUserStory;
import be.scryper.sos.infrastructure.ICommentRepository;
import be.scryper.sos.infrastructure.Retrofit;
import be.scryper.sos.ui.CommentArrayAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserStoryActivity extends AppCompatActivity {
    private ListView lvSimple;
    private TextView tvName;
    private TextView tvDescription;
    private Button btnAddComment;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_story);

        tvName = findViewById(R.id.tv_userStoryActivity_ph_name);
        tvDescription = findViewById(R.id.tv_userStoryActivity_ph_description);
        btnAddComment = findViewById(R.id.btn_userStoryActivity_addComment);

        DtoUserStory userStory = getIntent().getParcelableExtra(SprintActivity.KEY_USER_STORY);

        DtoAuthenticateResult authenticateResult = getIntent().getParcelableExtra(MainActivity.KEY_LOGIN);

        tvName.setText(userStory.getName());
        tvDescription.setText(userStory.getDescription());

        getComments(userStory.getId());

        btnAddComment.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            final EditText edittext = new EditText(getApplicationContext());
            String titleText = "Add comment";

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
            builder.setMessage("Content :")
                    .setCancelable(true)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if(edittext.getText().toString().matches("")){
                                Toast.makeText(
                                        getApplicationContext(),
                                        "can't send empty comment",
                                        Toast.LENGTH_LONG
                                ).show();

                                return;
                            }
                            int idUserStory = userStory.getId();
                            int idUser = authenticateResult.getId();

                            LocalDateTime postedAt = LocalDateTime.now();
                            postedAt = postedAt.truncatedTo(ChronoUnit.SECONDS);
                            String tmp = postedAt.toString();
                            DtoCreateComment newComment = new DtoCreateComment(idUserStory, idUser, tmp, edittext.getText().toString());
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
                        }
                    });


            //Creating dialog box
            AlertDialog alert = builder.create();
            alert.setView(edittext);

            //Setting the title manually
            alert.show();


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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        lvSimple.setOnItemClickListener((adapterView, view, i, l) -> {
            //Uncomment the below code to Set the message and title from the strings.xml file
            final EditText edittext = new EditText(getApplicationContext());
            String titleText = "Update comment";

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
            DtoComment comment = (DtoComment) adapterView.getItemAtPosition(i);


            // Set the alert dialog title using spannable string builder
            builder.setTitle(ssBuilder);
            //Setting message manually and performing action on button click
            builder.setMessage("Content :")
                    .setCancelable(true)
                    .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DtoCreateComment updatedComment = new DtoCreateComment(comment.getIdUserStory(), comment.getIdUser(), comment.getPostedAt(), edittext.getText().toString());
                            updateComment(comment.getId(), updatedComment);
                            dialog.cancel();
                        }
                    })
            .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteComment(comment.getId(), comment.getIdUserStory());
                dialog.cancel();
            }
            });


            //Creating dialog box
            AlertDialog alert = builder.create();
            alert.setView(edittext);
            edittext.setText(comment.getContent());

            //Setting the title manually
            alert.show();
        });
    }

    private void deleteComment(int commentId, int idUserStory) {
        Retrofit.getInstance().create(ICommentRepository.class)
                .delete(commentId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
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
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        t.toString(),
                        Toast.LENGTH_LONG
                ).show();

            }
        });
    }

    private void updateComment(int id, DtoCreateComment updatedComment) {
        Retrofit.getInstance().create(ICommentRepository.class)
                .updateContent(id, updatedComment).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    getComments(updatedComment.getIdUserStory());

                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "error",
                            Toast.LENGTH_LONG
                    ).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        t.toString(),
                        Toast.LENGTH_LONG
                ).show();

            }
        });
    }
}