package be.scryper.sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TimeZone;

import be.scryper.sos.dto.DtoAuthenticateResult;

public class HomeActivity extends AppCompatActivity {
    private static final int CALENDAR_PERMISSION_CODE = 100;

    private Button btnProject;
    private Button btnAgenda;
    private TextView tvFirstname;
    private TextView tvLastname;
    private TextView tvRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnProject = findViewById(R.id.btn_homeActivity_project);
        btnAgenda = findViewById(R.id.btn_homeActivity_agenda);
        tvFirstname = findViewById(R.id.tv_homeActivity_ph_firstname);
        tvLastname = findViewById(R.id.tv_homeActivity_ph_lastname);
        tvRole = findViewById(R.id.tv_homeActivity_ph_role);

        DtoAuthenticateResult authenticateResult = getIntent().getParcelableExtra(MainActivity.KEY_LOGIN);

        tvFirstname.setText(authenticateResult.getFirstname());
        tvLastname.setText(authenticateResult.getLastname());

        switch (authenticateResult.getRole()){
            case 1:
                tvRole.setText("Developer");
                break;

            case 2:
                tvRole.setText("Scrum Master");
                break;

            default:
                tvRole.setText("Product Owner");

        }

        btnProject.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ProjectActivity.class);
            intent.putExtra(MainActivity.KEY_LOGIN, authenticateResult);

            startActivity(intent);
        });
        btnAgenda.setOnClickListener(view -> {
            String EVENT_BEGIN_TIME_IN_MILLIS = "1639663607000";
            String EVENT_END_TIME_IN_MILLIS = "1639750007000";
/*
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.Events.TITLE, "TITLE") // Simple title
                    .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, Long.parseLong(EVENT_BEGIN_TIME_IN_MILLIS.toString())) // Only date part is considered when ALL_DAY is true; Same as DTSTART
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, Long.parseLong(EVENT_END_TIME_IN_MILLIS.toString())) // Only date part is considered when ALL_DAY is true
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, "Hong Kong")
                    .putExtra(CalendarContract.Events.DESCRIPTION, "DESCRIPTION") // Description
                    .putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE)
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_FREE);
            startActivity(intent);*/
            checkPermission(Manifest.permission.WRITE_CALENDAR, CALENDAR_PERMISSION_CODE);
            addEvent();
        });
    }

    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(HomeActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(HomeActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void addEvent() {
        String EVENT_BEGIN_TIME_IN_MILLIS = "1639663607000";
        String EVENT_END_TIME_IN_MILLIS = "1639750007000";
        final ContentValues event = new ContentValues();
        event.put(CalendarContract.Events.CALENDAR_ID, 1);

        event.put(CalendarContract.Events.TITLE, "test");
        event.put(CalendarContract.Events.DESCRIPTION, "description");
        event.put(CalendarContract.Events.EVENT_LOCATION, "location");

        event.put(CalendarContract.Events.DTSTART, Long.parseLong(EVENT_BEGIN_TIME_IN_MILLIS.toString()));
        event.put(CalendarContract.Events.DTEND, Long.parseLong(EVENT_END_TIME_IN_MILLIS.toString()));
        event.put(CalendarContract.Events.ALL_DAY, 0);   // 0 for false, 1 for true
        event.put(CalendarContract.Events.HAS_ALARM, 1); // 0 for false, 1 for true

        String timeZone = TimeZone.getDefault().getID();
        event.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);

        Uri baseUri;
        baseUri = Uri.parse("content://com.android.calendar/events");

        Context context = this.getApplicationContext();
        context.getContentResolver().insert(baseUri, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CALENDAR_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(HomeActivity.this, "Camera Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(HomeActivity.this, "Camera Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
    }
}