package be.scryper.sos.infrastructure;

import java.util.List;

import be.scryper.sos.dto.DtoSprintUserStory;
import be.scryper.sos.dto.DtoUserStory;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IUserStoryRepository {
    @GET("userStories/byId/{id}")
    Call<DtoUserStory> getById(@Path("id") int id);
}
