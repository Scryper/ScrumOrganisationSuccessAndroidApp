package be.scryper.sos.infrastructure;

import java.util.List;

import be.scryper.sos.dto.DtoSprintUserStory;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ISprintUserStoryRepository {
    @GET("sprintsUserStories/byIdSprint/{idSprint}")
    Call<List<DtoSprintUserStory>> getByIdSprint(@Path("idSprint") int id, @Header("Authorization")String token);
}
