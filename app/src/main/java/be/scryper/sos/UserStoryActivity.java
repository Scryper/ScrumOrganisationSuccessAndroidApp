package be.scryper.sos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.dto.DtoComment;
import be.scryper.sos.dto.DtoCreateComment;
import be.scryper.sos.dto.DtoUserStory;
import be.scryper.sos.helpers.SessionManager;
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
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_story);
        sessionManager = new SessionManager(this);
        tvName = findViewById(R.id.tv_userStoryActivity_ph_name);
        tvDescription = findViewById(R.id.tv_userStoryActivity_ph_description);
        btnAddComment = findViewById(R.id.btn_userStoryActivity_addComment);

        //get intent from SprintActivity and LoginActivity
        DtoUserStory userStory = getIntent().getParcelableExtra(SprintActivity.KEY_USER_STORY);
        DtoAuthenticateResult authenticateResult = getIntent().getParcelableExtra(LoginActivity.KEY_LOGIN);

        tvName.setText(userStory.getName());
        tvDescription.setText(userStory.getDescription());

        getComments(userStory.getId());

        //on click open alert to add a comment
        btnAddComment.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            final EditText edittext = new EditText(getApplicationContext());

            // Set the alert dialog title using spannable string builder
            builder.setTitle(setTextColor("Add comment", Color.BLUE));
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
                            //check if comment is empty
                            if(edittext.getText().toString().matches("")){
                                Toast.makeText(
                                        getApplicationContext(),
                                        "can not send empty comment",
                                        Toast.LENGTH_SHORT
                                ).show();
                                dialog.cancel();
                                return;
                            }
                            int idUserStory = userStory.getId();
                            int idUser = authenticateResult.getId();

                            Date postedAt = new Date();
                            String tmp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).format(postedAt);
                            Log.e("dotni",tmp);
                            DtoCreateComment newComment = new DtoCreateComment(idUserStory, idUser, tmp, edittext.getText().toString());
                            addComment(newComment, idUserStory);

                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            alert.setView(edittext);
            alert.show();

        });
    }

    //add a comment
    private void addComment(DtoCreateComment newComment, int idUserStory) {
        Retrofit.getInstance().create(ICommentRepository.class)
                .create(newComment, "Bearer "+sessionManager.fetchAuthToken()).enqueue(new Callback<DtoComment>() {
            @Override
            public void onResponse(Call<DtoComment> call, Response<DtoComment> response) {

                if (response.code() == 201) {
                    getComments(idUserStory);
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Something went wrong",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<DtoComment> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }

    //get the list of comments
    private void getComments(int id) {
        Retrofit.getInstance().create(ICommentRepository.class)
                .getByIdUserStory(id, "Bearer "+sessionManager.fetchAuthToken()).enqueue(new Callback<List<DtoComment>>() {
            @Override
            public void onResponse(Call<List<DtoComment>> call, Response<List<DtoComment>> response) {
                if (response.code() == 200) {
                    List<DtoComment> comments = response.body();
                    initCommentList(comments);

                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Something went wrong",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<List<DtoComment>> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }

    //init the listview with the list of comment
    private void initCommentList(List<DtoComment> comments) {
        lvSimple = findViewById(R.id.lv_userStoryActivity_simpleList);

        CommentArrayAdapter adapter = new CommentArrayAdapter(
                getApplicationContext(),
                comments
        );

        lvSimple.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DtoAuthenticateResult authenticateResult = getIntent().getParcelableExtra(LoginActivity.KEY_LOGIN);

        //on click open an alert to update or delete the comment
        lvSimple.setOnItemClickListener((adapterView, view, i, l) -> {
            //Uncomment the below code to Set the message and title from the strings.xml file
            final EditText edittext = new EditText(getApplicationContext());

            DtoComment comment = (DtoComment) adapterView.getItemAtPosition(i);

            if(authenticateResult.getId() != comment.getIdUser()){
                return;
            }

                // Set the alert dialog title using spannable string builder
            builder.setTitle(setTextColor("Update comment", Color.BLUE));

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
            alert.show();
        });
    }

    //used to change the color of the text in an alert
    private SpannableStringBuilder setTextColor(String content, int color) {
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);

        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(content);

        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                content.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        return ssBuilder;
    }

    //delete a comment
    private void deleteComment(int commentId, int idUserStory) {
        Retrofit.getInstance().create(ICommentRepository.class)
                .delete(commentId, "Bearer "+sessionManager.fetchAuthToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    getComments(idUserStory);

                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Something went wrong",
                            Toast.LENGTH_SHORT
                    ).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }

    //update a comment
    private void updateComment(int id, DtoCreateComment updatedComment) {
        Retrofit.getInstance().create(ICommentRepository.class)
                .updateContent(id, updatedComment, "Bearer "+sessionManager.fetchAuthToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    getComments(updatedComment.getIdUserStory());

                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Something went wrong",
                            Toast.LENGTH_SHORT
                    ).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }
}