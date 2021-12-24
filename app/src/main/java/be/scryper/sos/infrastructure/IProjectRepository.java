package be.scryper.sos.infrastructure;

import be.scryper.sos.dto.DtoProject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface IProjectRepository {

    //Get requests
    @GET("projects/byId/{id}")
    Call<DtoProject> getById(@Path("id") int id, @Header("Authorization")String token);

}
