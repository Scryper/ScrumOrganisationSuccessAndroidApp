package be.scryper.sos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.dto.DtoDeveloperProject;
import be.scryper.sos.dto.DtoProject;
import be.scryper.sos.dto.DtoSprint;
import be.scryper.sos.helpers.SessionManager;
import be.scryper.sos.infrastructure.IProjectRepository;
import be.scryper.sos.infrastructure.ISprintRepository;
import be.scryper.sos.infrastructure.IUserProjectRepository;
import be.scryper.sos.infrastructure.Retrofit;
import be.scryper.sos.ui.SprintArrayAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectActivity extends AppCompatActivity {
    public static final String KEY_SPRINT = "sprint";
    public static final String KEY_PROJECT = "projectName" ;

    private ListView lvSimple;
    private TextView tvName;
    private TextView tvDescription;
    private String projectName;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        sessionManager = new SessionManager(this);
        //get the intent from the login
        DtoAuthenticateResult authenticateResult = getIntent().getParcelableExtra(LoginActivity.KEY_LOGIN);

        //Get the project and fill the listview with the sprints
        getDeveloperProject(authenticateResult);
    }

    //get the id of the project of the user if there is one
    private void getDeveloperProject(DtoAuthenticateResult authenticateResult) {
        Retrofit.getInstance().create(IUserProjectRepository.class)
                .getByIdDeveloper(authenticateResult.getId(), "Bearer "+sessionManager.fetchAuthToken()).enqueue(new Callback<List<DtoDeveloperProject>>() {
            @Override
            public void onResponse(Call<List<DtoDeveloperProject>> call, Response<List<DtoDeveloperProject>> response) {

                if (response.code() == 200) {
                    List<DtoDeveloperProject> developerProjects = response.body();
                    for (DtoDeveloperProject developerProject :
                            developerProjects) {
                        //check if the user as applied for a project and has been accepted
                        if (!developerProject.isAppliance()) {
                            //get the project information
                            getProject(developerProject.getIdProject());
                            //get the sprint of the project
                            getSprints(developerProject.getIdProject());
                            return;
                        }
                    }
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Something went wrong",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<List<DtoDeveloperProject>> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }

    //get the information of the project
    private void getProject(int idProject) {
        Retrofit.getInstance().create(IProjectRepository.class)
                .getById(idProject, "Bearer "+sessionManager.fetchAuthToken()).enqueue(new Callback<DtoProject>() {
            @Override
            public void onResponse(Call<DtoProject> call, Response<DtoProject> response) {
                if (response.code() == 200) {
                    DtoProject project = response.body();
                    tvName = findViewById(R.id.tv_projectActivity_ph_name);
                    tvDescription = findViewById(R.id.tv_projectActivity_ph_description);
                    tvName.setText(project.getName());
                    tvDescription.setText(project.getDescription());
                    projectName = project.getName();

                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Something went wrong",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<DtoProject> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }

    //fill the listview with sprint
    private void initSprintList(List<DtoSprint> sprints) {
        lvSimple = findViewById(R.id.lv_projectActivity_simpleList);


        SprintArrayAdapter adapter = new SprintArrayAdapter(
                getApplicationContext(),
                sprints
        );

        lvSimple.setAdapter(adapter);
        //on click, go to SprintActivity
        lvSimple.setOnItemClickListener((adapterView, view, i, l) -> {
            DtoSprint sprint = (DtoSprint) adapterView.getItemAtPosition(i);
            DtoAuthenticateResult authenticateResult = getIntent().getParcelableExtra(LoginActivity.KEY_LOGIN);
            Intent intent = new Intent(ProjectActivity.this, SprintActivity.class);
            intent.putExtra(KEY_SPRINT, (Parcelable) sprint);
            intent.putExtra(LoginActivity.KEY_LOGIN, authenticateResult);
            intent.putExtra(KEY_PROJECT, projectName);

            startActivity(intent);
        });
    }

    //get the list of sprints of the project
    private void getSprints(int idProject) {

        Retrofit.getInstance().create(ISprintRepository.class)
                .getByIdProject(idProject, "Bearer "+sessionManager.fetchAuthToken()).enqueue(new Callback<List<DtoSprint>>() {
            @Override
            public void onResponse(Call<List<DtoSprint>> call, Response<List<DtoSprint>> response) {
                if (response.code() == 200) {
                    List<DtoSprint> sprints = response.body();
                    initSprintList(sprints);

                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Something went wrong",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<List<DtoSprint>> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }
}