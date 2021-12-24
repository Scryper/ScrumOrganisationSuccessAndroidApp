package be.scryper.sos.infrastructure;

import java.util.List;

import be.scryper.sos.dto.DtoSprint;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ISprintRepository {
    @GET("sprints/byProject/{idProject}")
    Call<List<DtoSprint>> getByIdProject(@Path("idProject") int id, @Header("Authorization")String token);
}
