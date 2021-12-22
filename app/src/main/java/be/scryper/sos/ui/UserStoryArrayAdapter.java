package be.scryper.sos.ui;

import android.annotation.SuppressLint;
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

    @SuppressLint("SetTextI18n")
    private void populateView(DtoUserStory userStory, View convertView) {
        TextView tvName = convertView.findViewById(R.id.tv_listItemUserStory_ph_name);
        TextView tvDescription = convertView.findViewById(R.id.tv_listItemUserStory_ph_description);
        TextView tvNumberUS = convertView.findViewById(R.id.tv_userStory_list_item_title);

        int priority = userStory.getPriority();
        int red = 255 - priority * 255 / 7;
        if(red < 0){
            red = 0;
        }
        String hexRed = Integer.toHexString(red);
        int green = priority * 255 / 7;
        if(green < 0){
            green = 0;
        }
        String hexGreen = Integer.toHexString(green);
        String color = "#" + hexRed + hexGreen + "00";

        tvNumberUS.setTextColor(Color.parseColor(color));

        tvName.setText(userStory.getName());
        tvDescription.setText(userStory.getDescription());
        tvNumberUS.setText("US "+userStory.getPriority()+" : ");
    }
}
