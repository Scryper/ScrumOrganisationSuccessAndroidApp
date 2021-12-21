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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private void populateView(DtoSprint sprint, View convertView) {
        TextView tvDate = convertView.findViewById(R.id.tv_listItemSprint_ph_date);
        TextView tvDescription = convertView.findViewById(R.id.tv_listItemSprint_ph_description);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime tmp = LocalDateTime.parse(sprint.getDeadline());
            tvDate.setText(tmp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }else{
            tvDate.setText(sprint.getDeadline());
        }
        tvDescription.setText(sprint.getDescription());
    }
}
