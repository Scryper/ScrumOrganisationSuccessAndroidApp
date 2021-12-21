package be.scryper.sos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.dto.DtoDeveloperProject;
import be.scryper.sos.dto.DtoProject;
import be.scryper.sos.dto.DtoSprint;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);


        DtoAuthenticateResult authenticateResult = getIntent().getParcelableExtra(MainActivity.KEY_LOGIN);

        getDeveloperProject(authenticateResult);


    }

    private void getDeveloperProject(DtoAuthenticateResult authenticateResult) {
        Retrofit.getInstance().create(IUserProjectRepository.class)
                .getByIdDeveloper(authenticateResult.getId()).enqueue(new Callback<List<DtoDeveloperProject>>() {
            @Override
            public void onResponse(Call<List<DtoDeveloperProject>> call, Response<List<DtoDeveloperProject>> response) {

                if (response.code() == 200) {
                    List<DtoDeveloperProject> developerProjects = response.body();
                    for (DtoDeveloperProject developerProject :
                            developerProjects) {
                        // TODO : check if true is the value for accepted in a project
                        if (!developerProject.isAppliance()) {
                            getProject(developerProject.getIdProject());
                            getSprints(developerProject.getIdProject());
                            return;
                        }
                    }
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
            public void onFailure(Call<List<DtoDeveloperProject>> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        t.toString(),
                        Toast.LENGTH_LONG
                ).show();

            }
        });
    }

    private void getProject(int idProject) {
        Retrofit.getInstance().create(IProjectRepository.class)
                .getById(idProject).enqueue(new Callback<DtoProject>() {
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
            public void onFailure(Call<DtoProject> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        t.toString(),
                        Toast.LENGTH_LONG
                ).show();

            }
        });
    }

    private void initSprintList(List<DtoSprint> sprints) {
        lvSimple = findViewById(R.id.lv_projectActivity_simpleList);


        SprintArrayAdapter adapter = new SprintArrayAdapter(
                getApplicationContext(),
                sprints
        );

        lvSimple.setAdapter(adapter);
        lvSimple.setOnItemClickListener((adapterView, view, i, l) -> {
            DtoSprint sprint = (DtoSprint) adapterView.getItemAtPosition(i);
            DtoAuthenticateResult authenticateResult = getIntent().getParcelableExtra(MainActivity.KEY_LOGIN);
            Intent intent = new Intent(ProjectActivity.this, SprintActivity.class);
            intent.putExtra(KEY_SPRINT, (Parcelable) sprint);
            intent.putExtra(MainActivity.KEY_LOGIN, authenticateResult);
            intent.putExtra(KEY_PROJECT, projectName);

            startActivity(intent);
        });
    }

    private void getSprints(int idProject) {

        Retrofit.getInstance().create(ISprintRepository.class)
                .getByIdProject(idProject).enqueue(new Callback<List<DtoSprint>>() {
            @Override
            public void onResponse(Call<List<DtoSprint>> call, Response<List<DtoSprint>> response) {
                if (response.code() == 200) {
                    List<DtoSprint> sprints = response.body();

                    initSprintList(sprints);
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
            public void onFailure(Call<List<DtoSprint>> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        t.toString(),
                        Toast.LENGTH_LONG
                ).show();

            }
        });
    }
}