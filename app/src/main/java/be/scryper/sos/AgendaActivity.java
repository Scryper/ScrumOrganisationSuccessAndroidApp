package be.scryper.sos;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.widget.CalendarView;

import java.text.ParseException;
import java.util.Calendar;

public class AgendaActivity extends AppCompatActivity {
    private CalendarView cvCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        cvCalendar = findViewById(R.id.scv_agendaActivity_calendar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                cvCalendar.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(Calendar.getInstance().getTime())).getTime(), true, true);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        cvCalendar.setFirstDayOfWeek(2);
    }
}