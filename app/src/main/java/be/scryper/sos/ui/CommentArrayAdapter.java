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
import be.scryper.sos.dto.DtoComment;

public class CommentArrayAdapter extends ArrayAdapter<DtoComment> {
    public CommentArrayAdapter(@NonNull Context context, @NonNull List<DtoComment> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_list_item, null);
        }

        DtoComment comment = getItem(position);
        populateView(comment, convertView);

        return convertView;
    }

    private void populateView(DtoComment comment, View convertView) {
        TextView tvContent = convertView.findViewById(R.id.tv_listItemComment_ph_content);
        TextView tvPostedAt = convertView.findViewById(R.id.tv_listeItemComment_ph_postedAt);

        tvContent.setText(comment.getContent());
        tvPostedAt.setText(String.valueOf(comment.getPostedAt()));
    }
}
