package be.scryper.sos.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import be.scryper.sos.R;
import be.scryper.sos.dto.DtoUserStory;

public class UserStoryArrayAdapter extends ArrayAdapter<DtoUserStory> {

    public UserStoryArrayAdapter(@NonNull Context context, @NonNull List<DtoUserStory> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.userstory_list_item, null);
        }

        DtoUserStory userStory = getItem(position);
        populateView(userStory, convertView);

        return convertView;
    }

    private void populateView(DtoUserStory userStory, View convertView) {
        TextView tvName = convertView.findViewById(R.id.tv_listItemUserStory_ph_name);
        TextView tvDescription = convertView.findViewById(R.id.tv_listItemUserStory_ph_description);
        //convertView.setBackgroundColor(Color.parseColor("#FCD5B5"));

        tvName.setText(userStory.getName());
        tvDescription.setText(userStory.getDescription());
    }
}
