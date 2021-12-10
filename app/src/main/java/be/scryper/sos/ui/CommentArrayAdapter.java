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
import be.scryper.sos.domain.Comment;

public class CommentArrayAdapter extends ArrayAdapter<Comment> {
    public CommentArrayAdapter(@NonNull Context context, @NonNull List<Comment> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_list_item, null);
        }

        Comment comment = getItem(position);
        populateView(comment, convertView);

        return convertView;
    }

    private void populateView(Comment comment, View convertView) {
        TextView tvContent = convertView.findViewById(R.id.tv_listItemComment_ph_content);
        TextView tvDate = convertView.findViewById(R.id.tv_listeItemComment_ph_date);

        tvContent.setText(comment.getTexte());
        tvDate.setText(String.valueOf(comment.getDate()));
    }
}
