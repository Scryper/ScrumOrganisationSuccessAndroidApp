package be.scryper.sos.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import be.scryper.sos.R;
import be.scryper.sos.domain.Comment;
import be.scryper.sos.domain.UserStory;

public class UserStoryArrayAdapter extends ArrayAdapter<UserStory> {

    public UserStoryArrayAdapter(@NonNull Context context, @NonNull List<UserStory> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.userstory_list_item, null);
        }

        UserStory userStory = getItem(position);
        populateView(userStory, convertView);

        return convertView;
    }

    private void populateView(UserStory userStory, View convertView) {
        TextView tvName = convertView.findViewById(R.id.tv_listItemUserStory_ph_name);
        TextView tvDescription = convertView.findViewById(R.id.tv_listItemUserStory_ph_description);
        TextView tvIsDone = convertView.findViewById(R.id.tv_listItemUserStory_ph_isDone);

        tvName.setText(userStory.getName());
        tvDescription.setText(userStory.getDescription());
        tvIsDone.setText(userStory.getDone());
    }
}
