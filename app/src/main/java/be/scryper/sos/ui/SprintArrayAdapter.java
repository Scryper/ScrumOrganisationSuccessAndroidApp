package be.scryper.sos.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import be.scryper.sos.R;
import be.scryper.sos.SprintActivity;
import be.scryper.sos.domain.Sprint;

public class SprintArrayAdapter extends ArrayAdapter<Sprint> {

    public SprintArrayAdapter(@NonNull Context context, @NonNull List<Sprint> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sprint_list_item, null);
        }

        Sprint sprint = getItem(position);
        populateView(sprint, convertView);

        return convertView;
    }

    private void populateView(Sprint sprint, View convertView) {
        TextView tvId = convertView.findViewById(R.id.tv_listItemSprint_ph_id);
        TextView tvDescription = convertView.findViewById(R.id.tv_listItemSprint_ph_description);

        tvId.setText(String.valueOf(sprint.getId()));
        tvDescription.setText(sprint.getDescription());
    }
}
