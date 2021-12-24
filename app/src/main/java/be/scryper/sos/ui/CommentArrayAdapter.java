package be.scryper.sos.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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
import be.scryper.sos.dto.DtoUser;
import be.scryper.sos.helpers.SessionManager;
import be.scryper.sos.infrastructure.IUserRepository;
import be.scryper.sos.infrastructure.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        TextView tvDate = convertView.findViewById(R.id.tv_listItemComment_ph_date);
        TextView tvTime = convertView.findViewById(R.id.tv_listItemComment_ph_time);
        TextView tvAuthor = convertView.findViewById(R.id.tv_listItemComment_ph_author);
        String dateTime = comment.getPostedAt();

        String[] datetimes = dateTime.split("T");
        String[] dates = datetimes[0].split("-");
        tvContent.setText(comment.getContent());
        tvDate.setText(dates[2]+"-"+dates[1]+"-"+dates[0]);
        tvTime.setText(datetimes[1]);

        SessionManager sessionManager = new SessionManager(this.getContext());
        Retrofit.getInstance().create(IUserRepository.class)
                .getById(comment.getIdUser(), "Bearer "+sessionManager.fetchAuthToken()).enqueue(new Callback<DtoUser>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<DtoUser> call, Response<DtoUser> response) {
                if (response.code() == 200) {
                    DtoUser dtoUser = response.body();
                    tvAuthor.setText(dtoUser.getFirstname() + "  " + dtoUser.getLastname() + " :");
                }
            }

            @Override
            public void onFailure(Call<DtoUser> call, Throwable t) {
                Log.e("Error", t.toString());
            }
        });

    }
}
