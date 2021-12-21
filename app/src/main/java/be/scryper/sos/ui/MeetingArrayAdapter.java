package be.scryper.sos.ui;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.format.DateTimeFormatter;
import java.util.List;

import be.scryper.sos.R;
import be.scryper.sos.dto.DtoMeeting;

public class MeetingArrayAdapter extends ArrayAdapter<DtoMeeting> {
    public MeetingArrayAdapter(@NonNull Context context, @NonNull List<DtoMeeting> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_meeting, null);
        }

        DtoMeeting meeting = getItem(position);
        populateView(meeting, convertView);

        return convertView;
    }


    private void populateView(DtoMeeting meeting, View convertView) {

        TextView tvDate = convertView.findViewById(R.id.tv_meeting_date);
        TextView tvDescription = convertView.findViewById(R.id.tv_meeting_description);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvDate.setText(meeting.getSchedule().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        }
        tvDescription.setText(meeting.getDescription());
    }
}
