package be.scryper.sos.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import be.scryper.sos.R;
import be.scryper.sos.dto.DtoSprint;

public class SprintArrayAdapter extends ArrayAdapter<DtoSprint> {

    public SprintArrayAdapter(@NonNull Context context, @NonNull List<DtoSprint> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sprint_list_item, null);
        }
        DtoSprint sprint = getItem(position);
        populateView(sprint, convertView);

        return convertView;
    }

    @SuppressLint("SimpleDateFormat")
    private void populateView(DtoSprint sprint, View convertView) {
        TextView tvDate = convertView.findViewById(R.id.tv_listItemSprint_ph_date);
        TextView tvDescription = convertView.findViewById(R.id.tv_listItemSprint_ph_description);

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(sprint.getDeadline());
            String tmp = new SimpleDateFormat("dd-MM-yyyy").format(date);
            tvDate.setText(tmp);
        } catch (ParseException e) {
            tvDate.setText(sprint.getDeadline());
            e.printStackTrace();
        }
        tvDescription.setText(sprint.getDescription());
    }
}
